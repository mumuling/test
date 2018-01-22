package com.zhongtie.work;

import com.zhongtie.work.util.Base64;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private String privateKey = "MIICXQIBAAKBgQCpq87z7ML9yBuIXh46ZWSH2+FgxoFZCqFF/3w99AdTHz0r/aa8" +
            "FExDkhY2wJFnGjctCEYZ1CrxMJsuEWQzMjighjKPefr3eqYvzpJ0kno5dh5v/+oj" +
            "/CKZVObbFGzNLUeMYpRmOr2ghXcoJ5ePg5rGNvDy+blYVDlt0qpovR1uhwIDAQAB" +
            "AoGBAKJuDA2Ql4uEynbeQ2n8LyWw5i5TWvj3KDVuxG4689gri9BVYA2mEOyPKhz4" +
            "dTqS6kd+KD4J06JaufR8ScSJS5MyBR2FUbUEH3FjhG/XwK9dDZgNvgwX7lLvnJ8g" +
            "QWSKPiDKYiUfuKkntHnnLwpPoh3YW6Th54D/Sq6syUdR3B3BAkEA2SH5HfHiKBpj" +
            "aMeqPwDkDK/zpM8UcnudualFc5pm31TAESpQBN4bIwMe+R6F10tl9Mun9J6aoX0k" +
            "aDCmvW9OaQJBAMgK7Puxn8TPUEc3ANrXRDfopXjVp6kkf5r7Mcl+FJXItZi+MUvV" +
            "mBZbSUKsvNZzMf5fmqU3ECA0NTZYcg93F28CQF9YZbXagz6s574Y/w8PaxSPJuPV" +
            "8tqaoZXnWJH82PN0mapv9BdPGId1VWJ7HrZ9a1XlH+lww+Hpu9TsmCtS9xECQHlc" +
            "p4nLqXJtk/agmBQspWaF7l3wwVcMnZ4Y54umwjZdHk8clCu92hc2KqwYwDgaiyoY" +
            "NmoljdL72Yyu/AQF7GECQQDIVypONLrJMTUeC6wOG0nrmlm88BtaGcLvS94TZ3kw" +
            "GO7TM8FXsyklMILtvmel71uJzS4PZYC0r7+iZfMY3p7m";

    @Test
    public void addition_isCorrect() throws Exception {
        System.out.print(Base64.encode("重庆".getBytes("GB2312")).trim());
    }
}