import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestNgCaseDate {

    @DataProvider(name = "value")
    public Object[][] createData1() {
        return new Object[][] {
                { "蓝桥云课-git" },
                { "蓝桥云课-maven" },
                { "蓝桥云课-ide" },
                { "蓝桥云课-selenium" },
                { "蓝桥云课-java" },
        };
    }

    @Test(dataProvider = "namedata",dataProviderClass = datMethod.class)
    public void test1(String name) {
        System.out.println("getValueName:" +name);
    }

static class datMethod{
    @DataProvider(name = "namedata")
    public static Object[][] classDate() {
        return new Object[][] {
                { "蓝桥云课-python" },
                { "蓝桥云课-ocr" },
        };
    }
}



    @Test
    public void errMethods() {
        int a = 1;
        int b = 0 ;
        System.out.println("我是被依赖的方法"+a/b);
    }

    @Test(alwaysRun = false,dependsOnMethods = {"errMethods"})
    public void test1() {
        System.out.println("测试用例1被执行");
    }


    /**
     * 如果serverStartedError方法执行失败则method1方法不被执行
     */
    @Test
    public void serverStartedError() {
        int a = 1;
        int b = 0 ;
        System.out.println("serverStartedError方法："+a/b);
    }
    @Test(alwaysRun = true,dependsOnMethods = { "serverStartedError" },priority=2)
    public void method1() {
    }

    @Test(threadPoolSize = 2, invocationCount = 2,  timeOut = 10000,priority=1)
    public void testServer() {
        System.out.println("蓝桥云课-java-selenium");
    }
    @Test
    public void priority() {
        System.out.println("未设置priority属性，默认执行");
    }

}
