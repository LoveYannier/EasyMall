function ajaxFunction(){
	var xmlHttp; 
	try{
		//����������������֧��
		xmlHttp = new XMLHTTPRequest();
	}catch(e){
		try{
			//IE6.0
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			try{
				//IE5.0������İ汾
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){
				//alert("����, ���õĵ�������һ���������??");
				throw e;
			}
		}
	}
	return xmlHttp;
}
