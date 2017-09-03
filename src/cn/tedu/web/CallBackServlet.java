package cn.tedu.web;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.OrderService;
import cn.tedu.utils.PaymentUtil;

public class CallBackServlet extends HttpServlet {
	private static Properties prop = null;
	static{
		prop = new Properties();
		try {
			prop.load(new FileReader(PayServlet.class.
					getClassLoader().getResource("merchantInfo.properties").getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//��ȡ�ص�ʱ�������ش���������Ϣ
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		//����id
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		//Ϊ��1��: ������ض���;
		//Ϊ��2��: ��������Ե�ͨѶ.
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// ���У�� --- �ж��ǲ���֧����˾֪ͨ��
		String hmac = request.getParameter("hmac");
		String keyValue = prop.getProperty("keyValue");
		//�����ϲ���������м��ܣ���֧����˾��������hmac���бȽϣ�û�б��޸ķ���true
		boolean isTrue = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur,
				r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue); 
		if(isTrue){//����û�б��۸�
			//���r9_BTypeΪ1��������ʾ��Ϣ
			if("1".equals(r9_BType)){//�ͻ���������ض���
				response.getWriter().println("<h1>���������ɣ��ȴ����лظ�ȷ����Ϣ������</h1>");
				//
				/*OrderService service = BasicFactory.getFactory().
						getInstance(OrderService.class);
				service.updatePayState(r6_Order,1);
				//�ظ�֧����˾
				response.getWriter().print("success");*/
			}else if("2".equals(r9_BType)){
				//��2��: ��������Ե�ͨѶ,�޸�֧��״̬
				System.out.println("����ɹ���");
				//�޸Ķ���״̬:δ����--���Ѹ���
				OrderService service = BasicFactory.getFactory().
						getInstance(OrderService.class);
				service.updatePayState(r6_Order,1);
				//�ظ�֧����˾
				response.getWriter().print("success");
			}
		}else{//������Ч
			System.out.println("���ݱ��۸�");
		}
	}

}
