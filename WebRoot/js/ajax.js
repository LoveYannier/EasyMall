function ajaxFunction(){
	var xmlHttp; 
	try{
		//绝大多数的浏览器都支持
		xmlHttp = new XMLHTTPRequest();
	}catch(e){
		try{
			//IE6.0
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			try{
				//IE5.0及更早的版本
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){
				//alert("哥们, 你用的到底是哪一个浏览器啊??");
				throw e;
			}
		}
	}
	return xmlHttp;
}
