package chapter01;

import java.util.Stack;

import edu.princeton.cs.algs4.StdIn;

/**
 * Dijkstra双栈算术求值
 * @author Administrator
 *
 */
public class Evaluate {
	
	public static double result() {
		//设置两个栈，一个存放数据，一个存放操作符
		Stack<Double> vals = new Stack<>();
		Stack<String> ops = new Stack<>();
		double result = 0;
		//遇到左括号忽略，右括号将数据栈栈顶两个数同操作符栈进行运算
		while(!StdIn.isEmpty()) {
			String s = StdIn.readString();
			switch (s) {
			case "(":
				break;
			case "+":
				ops.push("+");
				break;
			case "-":
				ops.push("-");
				break;
			case "*":
				ops.push("*");
				break;
			case "/":
				ops.push("/");
				break;
			case "sqrt":
				ops.push("sqrt");
				break;
			case ")":	//将数据栈操作
				result = vals.pop();
				String ss = ops.pop();
				switch (ss) {
				case "+":
					result = vals.pop() + result;
					vals.push(result);
					break;
				case "-":
					result = vals.pop() - result;
					vals.push(result);
					break;
				case "*":
					result = vals.pop() * result;
					vals.push(result);
					break;
				case "/":
					if(result == 0) {
						System.out.println("除数不能为0!");
						vals.push(0.0);
						break;
					}
					result = vals.pop() / result;
					break;
				case "sqrt":
					if(result < 0) {
						System.out.println("负数不能被开根!");
						vals.push(0.0);
						break;
					}
					result = Math.sqrt(result);
					break;

				default:	//将运算结果压入数据栈
					vals.push(result);
					break;
				}
				break;
				
			default:	//输入为数据
				double d = Double.parseDouble(s);
				vals.push(d);
				break;
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println("请输入算法表达式（两边得加括号）");
		double result = result();
		System.out.println(result);
	}

}
