package com.normal;

public class testStaticInnerClass {

	private static String name = "woobo";
	private String num = "X001";

	static class staticInnerPerson { // ��̬�ڲ��������public,protected,private����
		// ��̬�ڲ����п��Զ��徲̬���߷Ǿ�̬�ĳ�Ա
		private String address = "China";
		private static String x = "as";
		public String mail = "kongbowoo@yahoo.com.cn";// �ڲ��๫�г�Ա

		public void display() {
			// System.out.println(num);//����ֱ�ӷ����ⲿ��ķǾ�̬��Ա
			// ��̬�ڲ��಻�ܷ����ⲿ��ķǾ�̬��Ա(�����Ǿ�̬�����ͷǾ�̬����)
			System.out.println(name);// ֻ��ֱ�ӷ����ⲿ��ľ�̬��Ա
			// ��̬�ڲ���ֻ�ܷ����ⲿ��ľ�̬��Ա(������̬�����;�̬����)
			System.out.println("Inner " + address);// ���ʱ��ڲ����Ա��
		}
	}

	class normalInnerPerson {
		// ����Ǿ�̬�ĳ�Ա
		private String address = "China";
		// private static String x = "as";//�Ǿ�̬�ڲ��������о�̬��Ա
		public String mail = "kongbowoo@yahoo.com.cn";// �ڲ��๫�г�Ա

		public void display() {
			System.out.println(num);// ����ֱ�ӷ����ⲿ��ķǾ�̬��Ա
			System.out.println(name);// ��̬��Ա
			System.out.println("Inner " + address);// ���ʱ��ڲ����Ա��
		}
	}

	public void printInfo() {
		staticInnerPerson person = new staticInnerPerson();
		// �ⲿ������ڲ���ķǾ�̬��Ա:ʵ�����ڲ��༴��
		person.display();
		// System.out.println(mail);//���ɷ���
		// System.out.println(address);//���ɷ���
		System.out.println(person.address);// ʵ����֮��Ϳ��Է��ʣ����ҿ��Է����ڲ����˽�г�Ա
		System.out.println(staticInnerPerson.x);// �ⲿ������ڲ���ľ�̬��Ա���ڲ���.��̬��Ա
		System.out.println(person.mail);// ���Է����ڲ���Ĺ��г�Ա
	}

	public static void main(String[] args) {
		staticInnerPerson staticPerson = new staticInnerPerson();// ������ʵ�����ⲿ��
		testStaticInnerClass test = new testStaticInnerClass();
		normalInnerPerson normalPerson = test.new normalInnerPerson();// ������ʵ�����ⲿ��
	}
}
