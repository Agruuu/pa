package snippet;



public class demo {

	public static void main(String[] args) {
		String str = "/home/orhon/opa/upload/考核附件/2018/4/水务局/纠正&amp;quot;四风&amp;quot;不止步,驰而不息抓好作风建设.doc";
		StringBuilder io = new StringBuilder(str);
		//io.insert(3, "amp;");
		//io.insert(15, "amp;");
		//str = io.toString();
		//int t = str.indexOf("&");
		//System.out.println("长度"+str.length()+"||"+"suoyin="+t);
		String removerStr = "amp;";
		System.out.println(str.replace(removerStr,""));
		
		StringBuilder ttt = new StringBuilder();
		ttt.append("asdfghj");
		for(int i=ttt.length()-2;i>0;i=i-3) {
			ttt.insert(i, ",");
		}
		String strl = ttt.toString();
		System.out.println(strl);
	
	}

}
