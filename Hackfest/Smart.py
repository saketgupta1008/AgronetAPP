import tensorflow as tf
import numpy as np
import pandas as pd
import sys
from tensorflow.python.tools import freeze_graph
from tensorflow.python.tools import optimize_for_inference_lib


df1 = pd.read_csv('C:\\Users\\saket gupta\\Desktop\\New folder\\X_train.csv',sep=',',header=None)
X_trai = df1.values
X_train = X_trai[:,(0,2,3,4,8,9,10,11)];
df2 = pd.read_csv('C:\\Users\\saket gupta\\Desktop\\New folder\\y_train.csv',sep=',',header=None)
y_train = df2.values
df3 = pd.read_csv('C:\\Users\\saket gupta\\Desktop\\New folder\\X_test.csv',sep=',',header=None)
X_tes= df3.values
X_test = X_tes[:,(0,2,3,4,8,9,10,11)]
df4 = pd.read_csv('C:\\Users\\saket gupta\\Desktop\\New folder\\y_test.csv',sep=',',header=None)
y_test= df4.values


print(y_test.dtype,y_test.shape)#(409,1)
print(X_train.dtype,X_train.shape)#(951,12)


h  = tf.placeholder('float64',[8],name = 'X')
H = tf.cast(h,tf.float32)
H = tf.reshape(H,(1,8))
input_X = tf.placeholder('float64',[None,8],name= 'X_')
X = tf.cast(input_X,tf.float32)
input_y = tf.placeholder('int64',[None,1],name= 'y')
y = tf.cast(input_y,tf.int32)
y = tf.squeeze(tf.one_hot(y,34))

l1_nodes = 50

n_classes = 34
batch_size = 3
num_epoch=100
hidden_layer_1 = {'weights' : tf.Variable(tf.random_normal([8,l1_nodes])), 'baises' : tf.Variable(tf.random_normal([l1_nodes]))}

outer_layer = {'weights' : tf.Variable(tf.random_normal([l1_nodes,n_classes])), 'baises' : tf.Variable(tf.random_normal([n_classes]))}

l1 = tf.add(tf.matmul(X,hidden_layer_1['weights']),hidden_layer_1['baises'])
l1 = tf.nn.relu(l1)

output = tf.add(tf.matmul(l1,outer_layer['weights']),outer_layer['baises'],name = 'o')
Output = tf.cast(tf.argmax(output,1),tf.int32,name = "output_")

l1_ = tf.add(tf.matmul(H,hidden_layer_1['weights']),hidden_layer_1['baises'])
l1_ = tf.nn.relu(l1_)

output_ = tf.add(tf.matmul(l1_,outer_layer['weights']),outer_layer['baises'])
Output_ = tf.cast(tf.argmax(output_,1),tf.int32,name = "output")

saver = tf.train.Saver()
cost = tf.reduce_mean(tf.nn.softmax_cross_entropy_with_logits(logits = output,labels = y))
optimizer = tf.train.AdamOptimizer(0.005).minimize(cost)

sess = tf.Session()
sess.run(tf.global_variables_initializer())
tf.train.write_graph(sess.graph_def, '.', '/Agro/AgroNet.pbtxt',as_text=True)
for epoch in range(num_epoch):
	epoch_loss=0
	for i in range(0,951,batch_size):
		_,c = sess.run([optimizer,cost],{input_X:X_train[i:i+batch_size],input_y:y_train[i:i+batch_size]})
		epoch_loss += c
	print('epoch',epoch,'completed out of','num_epoch',epoch_loss)
saver.save(sess, '/Agro/AgroNet.ckpt')

correct = tf.equal(tf.argmax(output,1),tf.argmax(y,1))
accuracy = tf.reduce_mean(tf.cast(correct,'float32'))
print('Accuracy of train:',sess.run(accuracy,feed_dict = {input_X: X_train,input_y: y_train}))
print('Accuracy of test:',sess.run(accuracy,feed_dict = {input_X: X_test,input_y: y_test}))






writer = tf.summary.FileWriter("/Agro")
writer.add_graph(sess.graph)
print('Successful')



MODEL_NAME = 'AgroNet'

# Freeze the graph

input_graph_path = '/Agro/'+MODEL_NAME+'.pbtxt'
checkpoint_path = '/Agro/'+MODEL_NAME+'.ckpt'
input_saver_def_path = ""
input_binary = False
output_node_names = "output"
restore_op_name = "save/restore_all"
filename_tensor_name = "save/Const:0"
output_frozen_graph_name = 'frozen_'+MODEL_NAME+'.pb'
output_optimized_graph_name = 'optimized_'+MODEL_NAME+'.pb'
clear_devices = True
freeze_graph.freeze_graph(input_graph_path, input_saver_def_path,
                          input_binary, checkpoint_path, output_node_names,
                          restore_op_name, filename_tensor_name,
                          output_frozen_graph_name, clear_devices, "")

input_graph_def = tf.GraphDef()
with tf.gfile.Open(output_frozen_graph_name, "rb") as f:
    data = f.read()
    input_graph_def.ParseFromString(data)

#output_graph_def = optimize_for_inference_lib.optimize_for_inference(
#        input_graph_def,
#        ["input_X","input_y"], # an array of the input node(s)
#        ["output"], # an array of output nodes
#        tf.float32.as_datatype_enum)

# Save the optimized graph

#f = tf.gfile.FastGFile(output_optimized_graph_name, "wb")
#f.write(output_graph_def.SerializeToString())

sess.close()