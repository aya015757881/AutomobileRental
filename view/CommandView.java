package com.oaec.view;

public class CommandView
{
	/**
	 * �����û����ͣ���ʾ��Ӧ�Ĳ���������Ϣ��
	 * @param userType ��ʾ�û����͵��ַ���
	 */
	public static void showOptions(String userType)
	{
		if("user".equals(userType))
		{
			System.out.println("����0�˳�");
			System.out.println("����1+�������  �����⳵����  ��1+2");
			System.out.println("����2+1  �����������");
			System.out.println("����2+2  �������������");
			System.out.println("����3+���ͱ��  ����������");
			System.out.println("����4+Ʒ�Ʊ��  ��Ʒ������");
			System.out.println("����5  �鿴ȫ������");
			System.out.println("����6  �鿴�ҵ��⳵��¼");
			System.out.println("����7+�������  ����");
		}
		else
		{
			System.out.println("����0�˳�");
			System.out.println("����1+�������  �鿴ָ��������Ϣ");
			System.out.println("����2  �鿴ȫ��������Ϣ");
			System.out.println("����3+�������  �޸�ָ��������Ϣ");
			System.out.println("����4+�������  ɾ��ָ������");
			System.out.println("����5  ���һ��������");
			System.out.println("����6  �鿴ȫ�����޼�¼");
			System.out.println("����7+�������  �鿴ָ���������޼�¼");
			System.out.println("����8  �鿴ָ���û����޼�¼");
		}
	}
}
