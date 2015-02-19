public class Actvin {
	public static int  capacite, typeVin, nbChauf, vALivre,vLivre, chauffeurDuMois,nom, bj, bg, ordin, i,indiChauf;
	public static SmallSet mags;
	public static boolean present = false;

    private static class Chauffeur {
	public int numchauf, bj, bg, ordin ; 
	public SmallSet magdif ;
	public Chauffeur(int numchauf,int bj,int bg,int ordin,SmallSet magdif) {
	    this.numchauf = numchauf ; this.bj = bj ; this.bg = bg ; 
	    this.ordin = ordin ; this.magdif = magdif.clone() ;
	}
	
	public Chauffeur copie() {return new Chauffeur(numchauf,bj,bg,ordin,magdif);}
    } // class Chauffeur

    public static final int[][] action = {
	/* état        BJ    BG   IDENT  NBENT  ,    ;    /  AUTRES  */
	/* 0 */      {   -1,   -1,    1,   -1,   -1,   7,   8,   -1   },
	/* 1 */      {    3,    3,    4,    2,   -1,   7,   8,   -1   },
	/* 2 */      {    3,    3,    4,   -1,   -1,   7,   8,   -1   },
	/* 3 */		 {   -1,   -1,    4,   -1,   -1,   7,   8,   -1   },
	/* 4 */      {   -1,   -1,   -1,    5,   -1,   7,   8,   -1   },
	/* 5 */      {   -1,   -1,    4,   -1,    6,   7,   8,   -1   },
	/* -1 */     {   -1,   -1,   -1,   -1,   -1,   7,   8,   -1   }
    } ;	          

    private static final int FATALE = 0 , NONFATALE = 1 ;

    private static final int MAXCHAUF = 10 , MAXLGID = 20 ;
    private static Chauffeur[] tabchauf = new Chauffeur[MAXCHAUF] ;
 
    private static void attenteSurLecture(String mess) {
    Chauffeur c = new Chauffeur(1,2,3,4,new SmallSet());
	String tempo ;
	System.out.println("") ;
	System.out.print(mess + " pour continuer tapez entrée ") ;
	tempo = Lecture.lireString() ;
    } // attenteSurLecture

    private static void erreur(int te,String messerr) {
	attenteSurLecture(messerr) ;
	switch (te) {
	case FATALE    : Vin.errcontr = true ; break ;
	case NONFATALE : Vin.etat = Autovin.ETATERR ; break ;
	default : attenteSurLecture("paramétre incorrect pour erreur") ;
	}
    } // erreur
 
    private static String chaineCadrageGauche(String ch) {
	/* en entrée : ch est une chaine de longueur quelconque
	   délivre la chaine ch cadrée é gauche sur MAXLGID caractéres
	*/
	int lgch = Math.min(MAXLGID,ch.length()) ;
	String chres = ch.substring(0,lgch) ;
	for (int k = lgch ;k < MAXLGID ; k++) chres = chres + " " ;
	return chres ;
    } // chaineCadrageGauche
  
    public static void executer(int numact) {
   
    
	switch (numact) {
	case -1  : break ;
	case  0  :  nbChauf=0; mags = new SmallSet(); indiChauf = 0;
	           break ;
	case 1 :
			bj = 0; bg = 0; ordin = 0; typeVin = -1; capacite = 100; i=0; present = false;
			nom = Lexvin.numId;
			while(i < nbChauf && !present){
				if(tabchauf[i].numchauf == nom ){
					present = true;
				}
				else{
					i++;
				}
			}
			if(present){indiChauf=i;}
			else if(nbChauf >= MAXCHAUF){
				erreur(FATALE, "Trop de chauffeurs");
			}
		break;
	case 2 : capacite = Lexvin.valNb;
			if(capacite > 200 || capacite < 100){
				capacite = 100;
			}
		break;
		
	case 3 : typeVin = Lexvin.numId; 
		break;
		
	case 4 : mags.add(Lexvin.numId);
		break;
		
	case 5 : vALivre = Lexvin.valNb;

			 if(vALivre == 0){
				 erreur(NONFATALE, "La quantite à livrer est nulle");
			}
			 else if(vALivre + vLivre > capacite){
				 erreur(NONFATALE, "La capacite du camion a ete depassee. A livrer :" + vALivre + ", Quantité restante : " + vLivre + ", Capacité : " + capacite );
			 }
			 else{
				 	switch(typeVin){
					 	case 0 : bj += vALivre;
					 		break;
					 	case 1 : bg += vALivre;
				 			break;
					 	default : ordin += vALivre;
				 			break;
				 	}
				 	vLivre += vALivre;
			 }
		break;
	case 6 :	vLivre = 0; vALivre=0; typeVin=-1;
		break;
	
	case 7 :
			if(present){
				tabchauf[indiChauf].bg += bg;
				tabchauf[indiChauf].bj += bj;
				tabchauf[indiChauf].ordin += ordin;
				tabchauf[indiChauf].magdif.union(mags);
			}
			else{
				nbChauf++;
				tabchauf[nbChauf-1]= new Chauffeur(nom, bj, bg, ordin, mags);
			}
			System.out.println(chaineCadrageGauche("CHAUFFEURS") + chaineCadrageGauche("  BJ") + chaineCadrageGauche("  BG") + chaineCadrageGauche("  ORD") + chaineCadrageGauche("  NBMAG"));
			for(i = 0; i < nbChauf; i++){
				System.out.println(chaineCadrageGauche(Lexvin.repId(tabchauf[i].numchauf)) + chaineCadrageGauche("  " + tabchauf[i].bj) + chaineCadrageGauche(" "+ tabchauf[i].bg)
														+ chaineCadrageGauche(" " + tabchauf[i].ordin) + chaineCadrageGauche(" " + tabchauf[i].magdif.size()));
			}
			System.out.println("-------------------------------------------------------------------------");
			mags = new SmallSet();
			vLivre = 0; vALivre=0;
			
		break;
	case 8 : for(i = 0; i < nbChauf; i++){
				if(tabchauf[chauffeurDuMois].magdif.size() < tabchauf[i].magdif.size()){
					chauffeurDuMois=i;
				}
			}
			System.out.println("Le chauffeur du mois est : " + Lexvin.repId(tabchauf[chauffeurDuMois].numchauf) + ".");
		break;
	default : attenteSurLecture("action " + numact + " non prévue");
	}
    } // executer

    public static void main(String[] args) {
	System.out.println("la classe Actvin ne posséde pas de 'main'") ;
    } // main

} // class Actvin
