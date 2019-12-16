package me.theembers;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2019-11-05 11:02 下午
 */
public class SparkDemo {
    public static void main(String[] args) {
        //1.创建spark配置文件和上下文对象
        SparkConf conf = new SparkConf().setAppName("sparkTest").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        //2.读取日志文件并创建一个RDD，使用SparkContext的textFile（）方法
        JavaRDD<String> javaRDD = sc.textFile("D:\\app_log.txt");
    }
}
