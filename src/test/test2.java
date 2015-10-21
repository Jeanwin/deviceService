package test;



public class test2 {
	
	public static void main(String[] args) throws InterruptedException, Exception {
		String s="5d39467f43ab47f0ae1928e80dc4fc94,1__3b14b2eeb5d8405097ab39352116615d,192__63fe6e9a44fb4fcdbbd4a2b2c4f1045d,是为不胆1__808c591288a14fedbafafaa00cbc1191,2__8626d3f663a9480db2e3a20c17e9f053,三年二班__d533f8be73864145ab5662ed21eb01c3,二年一班__";
		String[] ss=s.split("__");
		for (int i = 0; i < ss.length; i++) {
			//String string = ss[i];
			System.out.println(ss[i]);
		}
		System.out.println(s.split("__").length);
	}

}
