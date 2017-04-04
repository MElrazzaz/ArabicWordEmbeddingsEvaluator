# ArabicWordEmbeddingsEvaluator

This Tool was build to test the accuracy of the wordembeddings on Arabic Benchmark regarding to "Lost In Embeddings: Methodical Evaluation of ArabicWord Embeddings" paper at ACL 2017

the benchmark consists of tuples and each tuple consits of N couples of words each couple consists of two words relats to each other by a relation for ex:
#ex1:
man woman king queen
رجل إمرأه ملك ملكة
#ex2:
dog dogs cat cats man men
كلب كلاب قط قطط رجل رجال
The test is done by predicting the last word using the the others
رجل to إمرأه is like ملك to ?
if the results shuld be ملكة the result considered to be true

#prereuqusits:
to run this test you will need JRE(java) instlled on your machine

#Usage
we have two formats of embeddings Glove fotmat and MOLKOVE format:
to test Molkove format :
1- chnage dirctory to the source code
2-type at the cmd java -jar MolkovFormatWordEmbeddingsAccuracy.java args1 args2 args3 args4 
args1 :is the wordembeddings file directory
args2:is the benchmark file directory
args3: is the results file directory
args4: number of words to consider the test is true(for top five words put '5')

to test Molkove format :
1- chnage dirctory to the source code
2-type at the cmd java -jar GloveFormatWordEmbeddingsAccuracy.java args1 args2 args3 args4 
args1 :is the wordembeddings file directory
args2: is the benchmark file directory
args3: is the results file directory
args4: number of words to consider the test is true(for top five words put '5')
