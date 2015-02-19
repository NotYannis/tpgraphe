import java.io.*;
import java.awt.*;

public class Lexvin {

	public static final int // codage des items
			BEAUJOLAIS = 0,
			BOURGOGNE = 1, IDENT = 2, NBENTIER = 3, VIRGULE = 4,
			PTVIRG = 5,
			BARRE = 6, AUTRES = 7;
	public static final String[] item = { "BEAUJ", "BOURG", "IDENT", "NBENT",
			"  ,  ", "  ;  ", "  /  ", "AUTRE" };
	
	public static int valNb, numId; // attributs lexicaux

	private static InputStream f; // fichier logique d'entrée
	private static TextArea fen; // fenétre d'entrée en cours d'analyse
	private static char carlu=' '; // caractére courant
	private static final int NBRES = 2; // nombre de mots réservés
	private static final int MAXID = 200; // nombre maximum d'ident
	private static String[] tabid = new String[MAXID + NBRES];
	// table des mots réservés suivis des ident
	private static int itab; // indice de remplissage de tabid

	private static void attenteSurLecture(String mess) {
		String tempo;
		System.out.println("");
		System.out.print(mess + " pour continuer tapez entrée ");
		tempo = Lecture.lireString();
	} // attenteSurLecture

	public static void debutAnalyse(TextArea fenentree) {
		String nomfich;
		fen = fenentree;
		carlu = ' ';
		itab = NBRES - 1;
		tabid[0] = "BEAUJOLAIS";
		tabid[1] = "BOURGOGNE";
		System.out.print("nom du fichier d'entrée : ");
		nomfich = Lecture.lireString();
		f = Lecture.ouvrir(nomfich);
		if (f == null) {
			attenteSurLecture("fichier " + nomfich + " inexistant");
			System.exit(0);
		}
	} // debutAnalyse

	public static void finAnalyse() {
		Lecture.fermer(f);
		attenteSurLecture("fin d'analyse");
	} // finAnalyse

	private static void lirecar() {
		carlu = Lecture.lireChar(f);
		if (carlu == '\r')
			carlu = ' ';
		fen.append("" + carlu); // la valeur carlu est transformée en chaéne
		if (Character.isWhitespace(carlu))
			carlu = ' ';
		else
			carlu = Character.toUpperCase(carlu);
	} // lirecar

	public static String repId(int nid) {
		if(tabid[nid] == ""){
			System.out.println("Pas d'identifiant pour cet id");
		}
		return tabid[nid];
	} // repId

	public static int liresymb() {
		int res = -1;
		while(carlu == ' '){ lirecar();}
		if(carlu >='0' && carlu <= '9'){
			valNb=0;
			do{valNb = valNb*10 + (carlu - '0'); lirecar();}
			while(carlu >= '0' && carlu <= '9');
			res = NBENTIER;
		}
		
		
		else if((carlu>= 'A' && carlu <= 'Z' )||(carlu>= 'a' && carlu <= 'z' )){
			String s = "";
			int i = 0;
			boolean present = false;
			do{s += carlu; lirecar();}
			while((carlu>= 'A' && carlu <= 'Z' )||(carlu>= 'a' && carlu <= 'z' ));
			switch(s){
			case "BEAUJOLAIS" :
				res= BEAUJOLAIS;
				numId = 0;
				break;
			case "BOURGOGNE" : 
				res= BOURGOGNE;
				numId = 1;
				break;
			default :
				while(i <= itab && !present){
					if(s.equals(tabid[i])){
						present = true;
						numId = i;
					}
					i++;
				}
				if(!present){
					itab++;
					tabid[itab]=s;
					numId = itab;
				}
				res = IDENT ;
				break;
			
			}
		}
		else{
			switch(carlu){
			case ',' :
				lirecar();
				res= VIRGULE;
				break;
			case ';' :
				lirecar();
				res  =PTVIRG;
				break;
			case '/' : 
				lirecar();
				res = BARRE;
				break;
			default  : 
				System.out.println("caractére non valide");
				lirecar();
				res = AUTRES;
				break;				
			}
			
			}
		
		return res ;
		}


	public static void main(String[] args) {
		System.out.println("la classe Lexvin ne posséde pas de 'main'");
	} // main
} // class Lexvin
