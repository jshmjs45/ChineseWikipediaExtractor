package wiki;


import java.util.Arrays;

public class Math {
	static String formatList[] = {"operatorname","mathrm","boldsymbol","mathbb","mathbf","mathit","mbox","mathfrak","mathcal"};
	
	
	public static String correctFormula(String line){
		if(line.contains("<math") || line.contains("</math>")) {
//			System.out.println(line);
//			String strs[] = line.split(" ?\\\\");
			//System.out.println(Arrays.toString(strs));
			
			//space
			line=line.replaceAll(" ?\\\\! ?","");
			line=line.replaceAll(" ?\\\\, ?","");
			line=line.replaceAll(" ?\\\\> ?","");
			line=line.replaceAll(" ?\\\\; ?","");
			line=line.replaceAll(" ?\\\\  ?"," ");
			line=line.replaceAll(" ?\\\\qq?uad  ?"," ");
			
			//Accents/diacritics
			line=line.replaceAll(" ?\\\\acute ?","");
			line=line.replaceAll(" ?\\\\grave ?","");
			line=line.replaceAll(" ?\\\\hat ?","");
			line=line.replaceAll(" ?\\\\widehat ?","");
			line=line.replaceAll(" ?\\\\vec ?","");
			line=line.replaceAll(" ?\\\\tilde ?","");
			line=line.replaceAll(" ?\\\\breve ?","");
			line=line.replaceAll(" ?\\\\check ?","");
			line=line.replaceAll(" ?\\\\bar ?","");		
			line=line.replaceAll(" ?\\\\ddot ?","");
			line=line.replaceAll(" ?\\\\acute ?","");
			line=line.replaceAll(" ?\\\\dot ?","");
			
			//Standard numerical functions
			line=line.replaceAll(" ?\\\\sin ?","sin");
			line=line.replaceAll(" ?\\\\cos ?","cos");
			line=line.replaceAll(" ?\\\\tan ?","tan");
			line=line.replaceAll(" ?\\\\sec ?","sec");
			line=line.replaceAll(" ?\\\\csc ?","csc");
			line=line.replaceAll(" ?\\\\cot ?","cot");
			line=line.replaceAll(" ?\\\\arcsin ?","arcsin");
			line=line.replaceAll(" ?\\\\arccos ?","arccos");
			line=line.replaceAll(" ?\\\\arctan ?","arctan");
			line=line.replaceAll(" ?\\\\sinh ?","sinh");
			line=line.replaceAll(" ?\\\\cosh ?","cosh");
			line=line.replaceAll(" ?\\\\tanh ?","tanh");
			line=line.replaceAll(" ?\\\\coth ?","coth");
			line=line.replaceAll(" ?\\\\arcsin ?","arcsin");
			line=line.replaceAll(" ?\\\\arcsin ?","arcsin");
			line=line.replaceAll(" ?\\\\arcsin ?","arcsin");
			line=line.replaceAll(" ?\\\\lim ?","lim");
			line=line.replaceAll(" ?\\\\limsup ?","limsup");
			line=line.replaceAll(" ?\\\\liminf ?","liminf");
			line=line.replaceAll(" ?\\\\min ?","min");
			line=line.replaceAll(" ?\\\\max ?","max");
			line=line.replaceAll(" ?\\\\inf ?","inf");
			line=line.replaceAll(" ?\\\\sup ?","sup");
			line=line.replaceAll(" ?\\\\exp ?","exp");
			line=line.replaceAll(" ?\\\\ln ?","ln");
			line=line.replaceAll(" ?\\\\lg ?","lg");
			line=line.replaceAll(" ?\\\\log ?","log");
			line=line.replaceAll(" ?\\\\ker ?","ker");
			line=line.replaceAll(" ?\\\\deg ?","deg");
			line=line.replaceAll(" ?\\\\gcd ?","gcd");
			line=line.replaceAll(" ?\\\\Pr ?","Pr");
			line=line.replaceAll(" ?\\\\det ?","det");
			line=line.replaceAll(" ?\\\\hom ?","hom");
			line=line.replaceAll(" ?\\\\arg ?","arg");
			line=line.replaceAll(" ?\\\\dim ?","dim");
			
			//Modular arithmetic
			line=line.replaceAll(" ?\\\\equiv ?","≡");
			line=line.replaceAll(" ?\\\\pmod ?","mod");
			line=line.replaceAll(" ?\\\\, ?\\\\bmod ?\\\\, ?"," mod");
			
			//Arrows
			line=line.replaceAll(" ?\\\\leftarrow ?","←");
			line=line.replaceAll(" ?\\\\gets ?","←");
			line=line.replaceAll(" ?\\\\rightarrow ?","→");
			line=line.replaceAll(" ?\\\\to ?","→");
			line=line.replaceAll(" ?\\\\nleftarrow ?","⇷");
			line=line.replaceAll(" ?\\\\nrightarrow ?","⇸");
			line=line.replaceAll(" ?\\\\leftrightarrow ?","↔");
			line=line.replaceAll(" ?\\\\longleftarrow ?","←");
			line=line.replaceAll(" ?\\\\longrightarrow ?","→");
			line=line.replaceAll(" ?\\\\longleftrightarrow ?","↔");
			
			line=line.replaceAll(" ?\\\\Leftarrow ?","⇐");
			line=line.replaceAll(" ?\\\\Rightarrow ?","⇒");
			line=line.replaceAll(" ?\\\\nLeftarrow ?","⇍");
			line=line.replaceAll(" ?\\\\nRightarrow ?","⇏");
			line=line.replaceAll(" ?\\\\Leftrightarrow ?","⇔");
			line=line.replaceAll(" ?\\\\nLeftrightarrow ?","⇎");
			line=line.replaceAll(" ?\\\\Longleftarrow ?","⟸");
			line=line.replaceAll(" ?\\\\Longrightarrow ?","⟹");
			line=line.replaceAll(" ?\\\\Longleftrightarrow ?","⟺");
			line=line.replaceAll(" ?\\\\iff ?","⟺");
			
			line=line.replaceAll(" ?\\\\uparrow ?","↑");
			line=line.replaceAll(" ?\\\\downarrow ?","↓");
			line=line.replaceAll(" ?\\\\updownarrow ?","↕");
			
			line=line.replaceAll(" ?\\\\nwarrow ?","↖");
			line=line.replaceAll(" ?\\\\nearrow ?","↗");
			line=line.replaceAll(" ?\\\\searrow ?","↘");
			line=line.replaceAll(" ?\\\\searrow ?","↙");
			line=line.replaceAll(" ?\\\\swarrow ?","⇑");
			line=line.replaceAll(" ?\\\\Downarrow ?","⇓");
			line=line.replaceAll(" ?\\\\Updownarrow ?","⇕");
			
			//Greek
			line=line.replaceAll(" ?\\\\Alpha ?" ,"Α");
			line=line.replaceAll(" ?\\\\Beta ?" ,"Β");
			line=line.replaceAll(" ?\\\\Gamma ?" ,"Γ");
			line=line.replaceAll(" ?\\\\Delta ?" ,"Δ");
			line=line.replaceAll(" ?\\\\Epsilon ?" ,"Ε");
			line=line.replaceAll(" ?\\\\Zeta ?" ,"Ζ");
			line=line.replaceAll(" ?\\\\Eta ?" ,"Η");
			line=line.replaceAll(" ?\\\\Theta ?" ,"Θ");
			line=line.replaceAll(" ?\\\\Iota ?" ,"Ι");
			line=line.replaceAll(" ?\\\\Kappa ?" ,"Κ");
			line=line.replaceAll(" ?\\\\Lambda ?" ,"Λ");
			line=line.replaceAll(" ?\\\\Mu ?" ,"Μ");
			line=line.replaceAll(" ?\\\\Nu ?" ,"Ν");
			line=line.replaceAll(" ?\\\\Xi ?" ,"Ξ");
			line=line.replaceAll(" ?\\\\Omicron ?" ,"Ο");
			line=line.replaceAll(" ?\\\\Pi ?" ,"Π");
			line=line.replaceAll(" ?\\\\Rho ?" ,"Ρ");
			line=line.replaceAll(" ?\\\\Sigma ?" ,"Σ");
			line=line.replaceAll(" ?\\\\Tau ?" ,"Τ");
			line=line.replaceAll(" ?\\\\Upsilon ?" ,"Υ");
			line=line.replaceAll(" ?\\\\Phi ?" ,"Φ");
			line=line.replaceAll(" ?\\\\Chi ?" ,"Χ");
			line=line.replaceAll(" ?\\\\Psi ?" ,"Ψ");
			line=line.replaceAll(" ?\\\\Omega ?" ,"Ω");
			line=line.replaceAll(" ?\\\\alpha ?" ,"α");
			line=line.replaceAll(" ?\\\\beta ?" ,"β");
			line=line.replaceAll(" ?\\\\gamma ?" ,"γ");
			line=line.replaceAll(" ?\\\\delta ?" ,"δ");
			line=line.replaceAll(" ?\\\\epsilon ?" ,"ϵ");
			line=line.replaceAll(" ?\\\\zeta ?" ,"ζ");
			line=line.replaceAll(" ?\\\\eta ?" ,"η");
			line=line.replaceAll(" ?\\\\theta ?" ,"θ");
			line=line.replaceAll(" ?\\\\iota ?" ,"ι");
			line=line.replaceAll(" ?\\\\kappa ?" ,"κ");
			line=line.replaceAll(" ?\\\\lambda ?" ,"λ");
			line=line.replaceAll(" ?\\\\mu ?" ,"μ");
			line=line.replaceAll(" ?\\\\nu ?" ,"ν");
			line=line.replaceAll(" ?\\\\xi ?" ,"ξ");
			line=line.replaceAll(" ?\\\\omicron ?" ,"ο");
			line=line.replaceAll(" ?\\\\pi ?" ,"π");
			line=line.replaceAll(" ?\\\\rho ?" ,"ρ");
			line=line.replaceAll(" ?\\\\sigma ?" ,"σ");
			line=line.replaceAll(" ?\\\\tau ?" ,"τ");
			line=line.replaceAll(" ?\\\\upsilon ?" ,"υ");
			line=line.replaceAll(" ?\\\\phi ?" ,"ϕ");
			line=line.replaceAll(" ?\\\\chi ?" ,"χ");
			line=line.replaceAll(" ?\\\\psi ?" ,"ψ");
			line=line.replaceAll(" ?\\\\omega ?" ,"ω");
			line=line.replaceAll(" ?\\\\varepsilon ?" ,"ε");
			line=line.replaceAll(" ?\\\\digamma ?" ,"Ϝ");
			line=line.replaceAll(" ?\\\\varkappa ?" ,"ϰ");
			line=line.replaceAll(" ?\\\\varpi ?" ,"ϖ");
			line=line.replaceAll(" ?\\\\varrho ?" ,"ϱ");
			line=line.replaceAll(" ?\\\\varsigma ?" ,"ς");
			line=line.replaceAll(" ?\\\\vartheta ?" ,"ϑ");
			line=line.replaceAll(" ?\\\\varphi ?" ,"φ");

			//Differentials and derivatives
			line=line.replaceAll(" ?\\\\nabla" ,"∇");
			line=line.replaceAll(" ?\\\\partial" ,"∂");
//			line=line.replaceAll("\\{d\\}" ,"𝑑");
			
			//Geometric
			line=line.replaceAll(" ?\\\\angle ?","∠");
			line=line.replaceAll(" ?\\\\Diamond ?","⋄");	
			line=line.replaceAll(" ?\\\\Box ?","□");
			line=line.replaceAll(" ?\\\\triangle ?","△");
			line=line.replaceAll(" ?\\\\prep ?","⟂");
			line=line.replaceAll(" ?\\\\mid ?","❘");
			line=line.replaceAll(" ?\\\\nmid ?","⟊");
			
			//Letter-like symbols or constants
			line=line.replaceAll(" ?\\\\infty ?","∞");
			line=line.replaceAll(" ?\\\\aleph ?","א");
			line=line.replaceAll(" ?\\\\complement ?","∁");
			line=line.replaceAll(" ?\\\\backepsilon ?","϶");
			line=line.replaceAll(" ?\\\\eth ?","ð");
			line=line.replaceAll(" ?\\\\hbar ?","ħ");
			line=line.replaceAll(" ?\\\\imath ?","i");
			line=line.replaceAll(" ?\\\\jmath ?","j");
			line=line.replaceAll(" ?\\\\Bbbk ?","𝕜");
			line=line.replaceAll(" ?\\\\ell ?","l");
			
			
			
			//format
			line=line.replaceAll(" ?\\\\operatorname ?","");
			line=line.replaceAll(" ?\\\\mathrm ?","");
			line=line.replaceAll(" ?\\\\boldsymbol ?","");
			line=line.replaceAll(" ?\\\\mathbb ?","");
			line=line.replaceAll(" ?\\\\mathbf ?","");
			line=line.replaceAll(" ?\\\\mathsf ?","");
			line=line.replaceAll(" ?\\\\mathit ?","");
			line=line.replaceAll(" ?\\\\mbox ?","");
			line=line.replaceAll(" ?\\\\mathfrak ?","");
			line=line.replaceAll(" ?\\\\mathcal ?","");
			line=line.replaceAll(" ?\\\\quad ?","");
			
			
			line=line.replaceAll(" ?\\\\scriptstyle ?","");
			line=line.replaceAll(" ?\\\\rm ?","");
			
			//Punctuation
			line=line.replaceAll(" ?\\\\left ?","");
			line=line.replaceAll(" ?\\\\right ?","");
			line=line.replaceAll(" ?\\\\langle ?","‹");
			line=line.replaceAll(" ?\\\\rangle ?","›");
			line=line.replaceAll(" ?\\\\vert ?","|");
			line=line.replaceAll(" ?\\\\Vert ?","‖");
			
			//Operators
			line=line.replaceAll(" ?\\\\oplus ?","⊕");
			line=line.replaceAll(" ?\\\\bigoplus" ,"⨁");
			line=line.replaceAll(" ?\\\\pm ?","±");
			line=line.replaceAll(" ?\\\\mp ?","∓");
			line=line.replaceAll(" ?\\\\times ?","×");
			line=line.replaceAll(" ?\\\\otimes ?","⊗");
			line=line.replaceAll(" ?\\\\bigotimes ?","⨂");
			line=line.replaceAll(" ?\\\\cdot ?","∙");
			line=line.replaceAll(" ?\\\\circ ?","∘");
			line=line.replaceAll(" ?\\\\bullet ?","∙");
			line=line.replaceAll(" ?\\\\bigodot ?","⊙");
			line=line.replaceAll(" ?\\\\star ?","⋆");
			line=line.replaceAll(" ?\\\\div ?","÷");
			line=line.replaceAll(" ?\\\\int ?","∫");
			
			//Logic
			line=line.replaceAll(" ?\\\\land ?","⋀");
			line=line.replaceAll(" ?\\\\and ?","⋀");
			line=line.replaceAll(" ?\\\\wedge ?","⋀");
			line=line.replaceAll(" ?\\\\lor ?","⋀");
			line=line.replaceAll(" ?\\\\vee ?","⋁");
			line=line.replaceAll(" ?\\\\bigvee ?","⋁");
			line=line.replaceAll(" ?\\\\lnot ?","¬");
			line=line.replaceAll(" ?\\\\neg ?","¬");
			line=line.replaceAll(" ?\\\\And ?","&");
			
			//Relations
			line=line.replaceAll(" ?\\\\ne ?","≠");
			line=line.replaceAll(" ?\\\\stackrel\\{def\\}\\{=\\} ?","=");
			line=line.replaceAll(" ?\\\\sim ?","∼");
			line=line.replaceAll(" ?\\\\approx ?","≈");
			line=line.replaceAll(" ?\\\\simeq ?","≃");
			line=line.replaceAll(" ?\\\\cong ?","≅");
			line=line.replaceAll(" ?\\\\dot= ?","≐");
			line=line.replaceAll(" ?\\\\le ?","≤");
			line=line.replaceAll(" ?\\\\ge ?","≥");
			line=line.replaceAll(" ?\\\\ll ?","≪");
			line=line.replaceAll(" ?\\\\gg ?","≫");
			
			//Sets
			line=line.replaceAll(" ?\\\\\\{ ?","\\{");
			line=line.replaceAll(" ?\\\\\\} ?","\\}");
			line=line.replaceAll(" ?\\\\forall ?","∀");
			line=line.replaceAll(" ?\\\\exists ?","∃");
			line=line.replaceAll(" ?\\\\empty ?","∅");
			line=line.replaceAll(" ?\\\\emptyset ?","∅");
			line=line.replaceAll(" ?\\\\varnothing ?","∅");
			line=line.replaceAll(" ?\\\\in ?","∈");
			line=line.replaceAll(" ?\\\\ni ?","∋");
			line=line.replaceAll(" ?\\\\not  ?","∉");
			line=line.replaceAll(" ?\\\\notin ?","∉");
			line=line.replaceAll(" ?\\\\subset ?","⊂");
			line=line.replaceAll(" ?\\\\subseteq ?","⊆");
			line=line.replaceAll(" ?\\\\supset ?","⊃");
			line=line.replaceAll(" ?\\\\supseteq ?","⊇");
			line=line.replaceAll(" ?\\\\cap ?","⋂");
			line=line.replaceAll(" ?\\\\bigcap ?","⋂");
			line=line.replaceAll(" ?\\\\cup ?","⋃");
			line=line.replaceAll(" ?\\\\bigcup ?","⋃");
			line=line.replaceAll(" ?\\\\biguplus ?","⨄");
			line=line.replaceAll(" ?\\\\setminus ?"," ?\\\\");
			line=line.replaceAll(" ?\\\\smallsetminus ?","∖");
			line=line.replaceAll(" ?\\\\sqsubset ?","⊏");
			line=line.replaceAll(" ?\\\\sqsubseteq ?","⊑");
			line=line.replaceAll(" ?\\\\sqsupset ?","⊐");
			line=line.replaceAll(" ?\\\\sqsupseteq ?","⊒");
			line=line.replaceAll(" ?\\\\sqcap ?","⊓");
			line=line.replaceAll(" ?\\\\sqcup ?","⊔");
			line=line.replaceAll(" ?\\\\bigsqcup ?","⊔");

			
			line=line.replaceAll(" ?\\\\over ?","/");

			line=line.replaceAll(" ?\\\\l?v?dots ?","…");
			
			line=line.replaceAll(" ?\\\\begin ?","");
			line=line.replaceAll(" ?\\\\end ?","");

			for(String format: formatList){
				long startTime=System.nanoTime();
				
				while(line.matches(".*\\\\"+format+"\\{.*\\}.*")){
//					System.out.println(format);
//					System.out.println(line);
					int beginIndexA = line.indexOf(format+"{");
					int endIndexA = line.indexOf("}",beginIndexA);
					
					String A = line.substring(beginIndexA+(format+"{").length(), endIndexA);
					while (Operation.countSubstr(A, "\\{") != Operation.countSubstr(A, "\\}")){				
						endIndexA = line.indexOf("}",endIndexA+1);
						A = line.substring(beginIndexA+(format+"{").length(), endIndexA);
					}
//					System.out.println(A);
//					System.out.println(B);
					line=line.replace("\\"+format+"{"+A+"}",A);
					
					double sDuration = (System.nanoTime() - startTime)/1000000000;
					if(sDuration>3){
						System.out.println(line);
						break;
					}
				}
			}
			
			long startTime=System.nanoTime();
			while(line.matches(".*\\^\\{.*\\}.*")){
				String A = "";
				System.out.println(line);
				int beginIndexA = line.indexOf("^{");
				int endIndexA = line.indexOf("}",beginIndexA);
				A = line.substring(beginIndexA+("^{").length(), endIndexA);
				//System.out.println(Operation.countSubstr(A, "\\{|\\}")%2);
				while (Operation.countSubstr(A, "\\{") != Operation.countSubstr(A, "\\}")){				
					endIndexA = line.indexOf("}",endIndexA+1);
					A = line.substring(beginIndexA+("^{").length(), endIndexA);
				}
				//System.out.println(A);
				
				line=line.replace("^{"+A+"}","^("+A+")");
				double sDuration = (System.nanoTime() - startTime)/1000000000;
				if(sDuration>300){
					System.out.println(line);
					break;
				}
			}
			
			startTime=System.nanoTime();
			while(line.matches(".*\\\\[dct]?frac\\{.*\\}\\{.*\\}.*")){
				int beginIndexA = line.indexOf("frac{");
				int endIndexA = line.indexOf("}",beginIndexA);
				String A = line.substring(beginIndexA+"frac{".length(), endIndexA);
				while (Operation.countSubstr(A, "\\{") != Operation.countSubstr(A, "\\}")){				
					endIndexA = line.indexOf("}",endIndexA+1);
					A = line.substring(beginIndexA+("frac{").length(), endIndexA);
				}
//				System.out.println(A);
				int beginIndexB = line.indexOf("{",endIndexA);
				int endIndexB = line.indexOf("}",beginIndexB);
				String B = line.substring(beginIndexB+"{".length(), endIndexB);
				while (Operation.countSubstr(B, "\\{") != Operation.countSubstr(B, "\\}")){				
					endIndexB = line.indexOf("}",endIndexB+1);
					B = line.substring(beginIndexB+("{").length(), endIndexB);
				}
//				System.out.println(B);
				String A2 = A;
				String B2 = B;
				if(A.contains("+") || A.contains("-")) A2="("+A+")";
				if(B.contains("+") || B.contains("-")) B2="("+B+")";
				line=line.replace("\\tfrac{"+A+"}{"+B+"}",A2+"/"+B2);
				line=line.replace("\\cfrac{"+A+"}{"+B+"}",A2+"/"+B2);
				line=line.replace("\\dfrac{"+A+"}{"+B+"}",A2+"/"+B2);
				line=line.replace("\\frac{"+A+"}{"+B+"}",A2+"/"+B2);
				double sDuration = (System.nanoTime() - startTime)/1000000000;
				if(sDuration>3){
					System.out.println(line);
					break;
				}
			}
			
			startTime=System.nanoTime();
			while(line.matches(".*\\\\sqrt(\\[.*\\])?\\{.*\\}.*")){
				int beginIndexA=0;
				int endIndexA=0;
				int beginIndexB=0;
				int endIndexB=0;
				String A = "";
				String B = "";
				if(line.contains("sqrt{")){
					beginIndexA = line.indexOf("sqrt{");
					endIndexA = line.indexOf("}",beginIndexA);
					A = line.substring(beginIndexA+"sqrt{".length(), endIndexA);
					while (Operation.countSubstr(A, "\\{") != Operation.countSubstr(A, "\\}")){				
						endIndexA = line.indexOf("}",endIndexA+1);
						A = line.substring(beginIndexA+("sqrt{").length(), endIndexA);
					}
//					System.out.println(A);
					line=line.replace("\\sqrt{"+A+"}","√("+A+")");
				}
				else if(line.contains("sqrt[")){
					beginIndexA = line.indexOf("sqrt[");
					endIndexA = line.indexOf("]",beginIndexA);
					A = line.substring(beginIndexA+"sqrt[".length(), endIndexA);
					while (Operation.countSubstr(A, "\\[") != Operation.countSubstr(A, "\\]")){				
						endIndexA = line.indexOf("]",endIndexA+1);
						A = line.substring(beginIndexA+("sqrt[").length(), endIndexA);
					}
					beginIndexB = line.indexOf("]{",endIndexA);
					endIndexB = line.indexOf("}",beginIndexB);
					B = line.substring(beginIndexB+"]{".length(), endIndexB);
					while (Operation.countSubstr(B, "\\{") != Operation.countSubstr(B, "\\}")){				
						endIndexB = line.indexOf("}",endIndexB+1);
						B = line.substring(beginIndexB+("]{").length(), endIndexB);
					}
//					System.out.println(A);
//					System.out.println(B);
//					if(A.equals("3"))line=line.replace("\\sqrt["+A+"]{"+B+"}","∛("+B+")");
//					else if(A.equals("4"))line=line.replace("\\sqrt["+A+"]{"+B+"}","∜("+B+")");
					line=line.replace("\\sqrt["+A+"]{"+B+"}","√["+A+"]("+B+")");
				}
				
				double sDuration = (System.nanoTime() - startTime)/1000000000;
				if(sDuration>3){
					System.out.println(line);
					break;
				}
			}
			
			startTime=System.nanoTime();
			while(line.matches(".*\\^\\{.*\\}.*")){
				String A = "";
				System.out.println(line);
				int beginIndexA = line.indexOf("^{");
				int endIndexA = line.indexOf("}",beginIndexA);
				A = line.substring(beginIndexA+("^{").length(), endIndexA);
				//System.out.println(Operation.countSubstr(A, "\\{|\\}")%2);
				while (Operation.countSubstr(A, "\\{") != Operation.countSubstr(A, "\\}")){				
					endIndexA = line.indexOf("}",endIndexA+1);
					A = line.substring(beginIndexA+("^{").length(), endIndexA);
				}
				//System.out.println(A);
				
				line=line.replace("^{"+A+"}","^("+A+")");
				double sDuration = (System.nanoTime() - startTime)/1000000000;
				if(sDuration>3){
					System.out.println(line);
					break;
				}
			}
			
			line=line.replaceAll("< ?/? ?math ?>","");
			line = line.replaceAll("\\s+", " ");
			
//			line.replaceAll("\\\\ ?","");
			
//			System.out.println(line+"\n");
			
			
			
		}
		
		
		return line;
		
	}
}
