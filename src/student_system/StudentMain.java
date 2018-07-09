package student_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 *  学生管理系统
 *   学生的CRUD 文件io流之缓冲流读写
 *   init时读取文件到内存,中途增删改都基于内存,退出系统时再做持久化,保存到文件中
 * @author yejincheng
 *
 */
public class StudentMain {
	
	//学生集合 <id, student>
	private static Map<Integer,Student> stuMap ;
	private static Scanner scan ;
	private static String filePath = "/Users/yejincheng/Desktop/1.txt";
	private static int deafaultListSize = 30;
	
	
	public static void main(String[] args) throws Exception {

		//初始化
		init(filePath,deafaultListSize);
		
		while(true) {
			//显示功能目录
			displayCatalog();
			//输入选项
			String in = scan.nextLine();
			int index = Integer.parseInt(in);
			switch (index) {
				case 1:
						//显示所有学生信息
						displayAllStudent();
						break;
				case 2:
					  	//删除学生信息
						delete();
						break;
				case 3:
						//修改学生信息
						update();
						break;
				case 4:
						//增加学生信息
						add();
						break;
				default:
						//保存并退出系统
						saveAndQuit();
						break;
				}
		}
	}
	
	/**
	 * service 
	 */
	private static void add() {
		
		Student add_stu = new Student();
		
		//录入信息
		System.out.println("请输入要增加学生的的学号");
		String add_id = scan.nextLine();
		add_stu.setId(Integer.parseInt(add_id));
		
		System.out.println("请输入要增加学生的的姓名");
		String add_name = scan.nextLine();
		add_stu.setName(add_name);
		
		System.out.println("请输入要增加学生的的性别");
		String add_sex = scan.nextLine();
		add_stu.setSex(add_sex);
		
		System.out.println("请输入要增加学生的的地址");
		String add_address = scan.nextLine();
		add_stu.setAddress(add_address);
		
		System.out.println("请输入要增加学生的的年龄");
		String add_age = scan.nextLine();
		add_stu.setAge(Integer.parseInt(add_age));
		
		//添加到map中
		String add_msg = addStudent(add_stu)?"添加成功":"添加失败";
		//打印操作日志
		System.out.println(add_msg);
	}

	private static void update() {
		System.out.println("请输入需要修改学生的id");
		String id2 = scan.nextLine();
		if(queryStudentById(Integer.parseInt(id2))!=null) {
			System.out.println("该学生不存在,无法修改");
		}else {
			Student stuNew = new Student();
			
			System.out.println("请输入新的name");
			String name = scan.nextLine();
			
			System.out.println("请输入新的address");
			String address = scan.nextLine();
			
			System.out.println("请输入新的age");
			String age = scan.nextLine();
		
			System.out.println("请输入新的sex");
			String sex = scan.nextLine();
			
			stuNew.setAddress(address);
			stuNew.setAge(Integer.parseInt(age));
			stuNew.setId(Integer.parseInt(id2));
			stuNew.setName(name);
			stuNew.setSex(sex);

			String mes = updateStudentById(Integer.parseInt(id2), stuNew)? "修改成功":"修改失败";
			System.out.println(mes);
		}
	}

	private static void delete() {
		System.out.println("请输入需要删除学生的id");
	  	String id1 = scan.nextLine();
	  	String message = removeStudentById(Integer.parseInt(id1))? "删除成功":"删除失败";
	  	System.out.println(message);
	}
	
	private static void saveAndQuit() throws Exception {
		System.out.println("正在保存信息,退出系统...");
		savaInfo();
		System.out.println("保存完毕!");
		System.exit(0);
	}

	private static void savaInfo() throws Exception{
		FileWriter fw = new FileWriter(filePath);
		BufferedWriter writer = new BufferedWriter(fw);
		for (Map.Entry<Integer, Student>  s1: stuMap.entrySet()) {
			writer.write(s1.getValue().toString());
			writer.newLine();
		}
		writer.close();
		fw.close();		
	}

	
	/**
	 *  basic service
	 */
	
	//初始化系统
	public static void init(String filePath,int deafaultListSize)throws Exception {
		scan = new Scanner(System.in);
		stuMap = new HashMap<>(deafaultListSize);
		//读取本地文件
		FileReader fr = new FileReader(filePath);
		BufferedReader reader = new BufferedReader(fr);
		String line;
		while((line =reader.readLine())!=null) {
			String[] arr = line.split("\t");
			
			//封装student
			Student ss = new Student();
			ss.setId(Integer.parseInt(arr[0]));
			ss.setName(arr[1]);
			ss.setSex(arr[2]);
			ss.setAddress(arr[3]);
			ss.setAge(Integer.parseInt(arr[4]));
			
			//add to map
			stuMap.put(ss.getId(), ss);
		}
		reader.close();
		fr.close();
	}
		
	//打印目录
	public static void displayCatalog() {
		System.out.println("===================学生管理系统====================");
		System.out.println("\t(1) 显示所有学生信息");
		System.out.println("\t(2) 删除学生信息");
		System.out.println("\t(3) 修改学生信息");
		System.out.println("\t(4) 添加学生信息");
		System.out.println("\t(5) 退出");
		System.out.println("=================================================");
		
	}
		
	//增加学生信息
	public static boolean addStudent(Student stu) {
		if(queryStudentById(stu.getId())==null) {
			stuMap.put((int) stu.getId(), stu);
			return true;
		}else {
			return false;
		}
	}
	
	//删除学生信息
	public static boolean removeStudentById(int id) {
		if(queryStudentById(id)!=null) {
			stuMap.remove(id);
			return true;
		}else {
			return false;
		}
	}
	
	//打印所有学生信息
	public static void displayAllStudent() {
		if(stuMap.size()<=0) {
			System.out.println("\t No student Now!");
		}else {
			System.out.println("一共有"+stuMap.size()+"位学生!");
			System.out.println("------------------------------------------");
			System.out.println("id\t"+"name\t"+"age\t"+"address\t"+"age\t");
			System.out.println("------------------------------------------");
			//遍历
			for (Map.Entry<Integer, Student>  s: stuMap.entrySet()) {
				Student s1 = s.getValue();
				System.out.print(s1.getId()+"\t");
				System.out.print(s1.getName()+"\t");
				System.out.print(s1.getAddress()+"\t");
				System.out.print(s1.getAge()+"\t");
				System.out.println(s1.getSex()+"\t");
				System.out.println("------------------------------------------");
			}
			
		}
	}
	
	//修改学生信息
	public static boolean updateStudentById(int id,Student stu) {
		for (Map.Entry<Integer, Student>  s: stuMap.entrySet()) {
			if(s.getKey().equals(id)) {
				s.setValue(stu);
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

	//查看根据id查看学生信息
	public static Student queryStudentById(int id) {
		for (Map.Entry<Integer, Student>  s: stuMap.entrySet()) {
			if(s.getKey().equals(id)) {
				return s.getValue();
			}else {
				return null;
			}
		}
		return null;	
	}
}
