# ArabicWordEmbeddingsEvaluator

This Tool was build to test the accuracy of the word embeddings on Arabic Benchmark regarding to "Methodical Evaluation of ArabicWord Embeddings" paper at ACL 2017

the benchmark consists of tuples and each tuple consists of N couples of words each couple consists of two words relats to each other by a relation for ex:

#ex of benchmark file:
: plurals
مقال	مقالات	خمر	خمور
مقال	مقالات	مخيم	مخيمات
: past tense
عمل	عمل	رؤية	رأي
عمل	عمل	قراءة	قرأ
عمل	عمل	مقاومة	قاوم
: opposite
نام	إستيقظ	أصبح	أمسى
نام	إستيقظ	ذكر	أنثي
نام	إستيقظ	جميل	قبيح
نام	إستيقظ	متسرع	هادئ
نام	إستيقظ	غبي	ذكي

*the lines that Stats with ":" is the relation name and they will be neglicted on the test but they will be usful to the results details for each relation.


The test is done by predicting the last word using the the others
رجل to إمرأه is like ملك to ? and the word considered true if it was ملكة  in the top 'n' answers

#prereuqusits:
to run this test you will need JRE(java runt time environment) 1.8 + instlled on your machin, to install JRE or JDK please fllow the insructions here https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html.

#Usage: to use the jar please fllow the instrcutions below
*you will find the jar at project dist folder
1- chnage dirctory to the source code
2-type at the terminal java -jar WordEmbeddingsEvaluator.jar -e "embeddings file" -b "benchmark file" -n "no of ansewer to consider the question is crroect" -g "t for glove format f for molokov format"
EX:
java -jar WordEmbeddingsEvaluator.jar  -e d:\Users\ME16683\Desktop\QU-Work\Data\RamiEmbeddings\arabic.w2v.txt -b d:\Users\ME16683\Desktop\QU-Work\Data\ourArabicBenchMark\TuplesBuckwalter\comparative.txt.buck.ALLCombin  -o D:\Users\ME16683\Desktop\result.txt -n 5 -g f

