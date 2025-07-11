package pLFileCreater;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class PlCreater {
/*
	public static void main(String[] args) {
	
		Auto[] autoArr = new Auto[] {new Auto("a"), new Auto("b")};
		String path = "C:\\Users\\karat\\Documents\\Prolog\\java.pl";
		
		
		try {
			String prolog = createProlog(4, autoArr );
			
			createPLFile(prolog, path);
			
			
			
		} catch (AnzahlStrassenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
*/
	public static String createProlog(int anzahlStrassen, ArrayList<Auto> autos, ArrayList<String> hauptStrasse) throws AnzahlStrassenException {
		String alph = "abcdefghij";
		String prolog = "";
		if(anzahlStrassen > 10) {
			throw new AnzahlStrassenException();
		}
		
		
		if(hauptStrasse != null) {
		
		for(int i = 0; i < autos.size(); i++) {
			prolog += "richtung(a" +i +"," + autos.get(i).getZufahrt() +","+ autos.get(i).getAbfahrt() + "). \r\n";
		}
		
		for(int i= 0; i < anzahlStrassen; i++) {
			prolog += "rechtsVon("+ alph.charAt(i)+"," + alph.charAt((i+1)%anzahlStrassen) ;
			prolog += ")." + "\r\n";
		}
		prolog += "\r\n ";

		
			prolog += "hauptStrasse(" + hauptStrasse.get(0) + "," + hauptStrasse.get(1) + ").\r\n";
		
			prolog += "\r\n"
					+ "biRichtung(H1,H2) :- richtung(_,H1, H2) ; richtung(_,H2, H1).\r\n"
					+ "aufHauptStrasse(A):- hauptStrasse(A,_); hauptStrasse(_,A).\r\n"
					+ "nichtAufHauptStrasse(A):- \\+ aufHauptStrasse(A).\r\n"
					+ "\r\n"
					+ "\r\n"
					+"entlangHauptStrasse(X) :- richtung(X,Z,A), aufHauptStrasse(Z), aufHauptStrasse(A).\r\n"
					+ "nichtEntlangHauptStrasse(X):- \\+ entlangHauptStrasse(X).\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "member(X, [X|_]).\r\n"
					+ "member(X, [_|T]):- member(X,T).\r\n"
					+ "\r\n"
					+ "memberOr(X,Y,Liste) :- member(X, Liste); member(Y, Liste).\r\n"
					+ "\r\n"
					+ "zwischen1h(X,X, []).\r\n"
					+ "zwischen1h(X,Y, []):- rechtsVon(X,Y).\r\n"
					+ "zwischen1h(X,Y, [Hz|Tz]):- rechtsVon(Hz, Y), zwischen1h(X, Hz, Tz).\r\n"
					+ "\r\n"
					+ "zwischen1V(X,Y, [X|Z]):- zwischen1h(X,Y,Z).\r\n"
					+"zwischen1N(X,Y, [Y|Z]):- zwischen1h(X,Y,Z).\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "zwischen2h(X,X, []).\r\n"
					+ "zwischen2h(X,Y, []):- rechtsVon(Y,X).\r\n"
					+ "zwischen2h(X,Y, [Hz|Tz]):- rechtsVon(Y, Hz), zwischen2h(X, Hz, Tz).\r\n"
					+ "\r\n"
					+ "zwischen2V(X,Y, [X|Z]):- zwischen2h(X,Y,Z).\r\n"
					+ "zwischen2(X,Y,[X|[Y|Z]]):- zwischen2h(X,Y,Z).\r\n"
					+"zwischen2N(X,Y, [Y|Z]):- zwischen2h(X,Y,Z).\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "haltenRvL(X):- richtung(X,Z,A), zwischen1h(Z,A, Dz1), zwischen2(Z,A, Dz2)\r\n"
					+ "          ,!\r\n"
					+ "          ,nichtAufHauptStrasse(Z)\r\n"
					+ "          ,richtung(_,Z1, A1)\r\n"
					+ "          ,member(Z1, Dz1)\r\n"
					+ "          ,member(A1, Dz2).\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "\r\n"
					+"haltenBeiWenden(X) :- richtung(X,Z,Z), richtung(A1,_,Z), A1\\= X.\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "haltenHauptQueren(X) :- hauptStrasse(H1, H2)\r\n"
					+ "                  ,biRichtung(H1,H2)\r\n"
					+ "                  ,zwischen1V(H1,H2, Dz1), zwischen2N(H1,H2, Dz2)\r\n"
					+ "                  ,!\r\n"
					+ "                  ,richtung(X,Z,A)\r\n"
					+ "                  ,memberOr(Z,A,Dz1)\r\n"
					+ "                  ,memberOr(Z,A,Dz2).\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "haltenHauptAuffahren1(X) :- hauptStrasse(H1, H2)\r\n"
					+ "                  ,richtung(_,H1,H2)\r\n"
					+ "                  ,richtung(X,Z,H2)\r\n"
					+ "                  ,Z \\= H1.\r\n"
					+ "\r\n"
					+ "haltenHauptAuffahren2(X) :- hauptStrasse(H1, H2)\r\n"
					+ "                  ,richtung(_,H2,H1)\r\n"
					+ "                  ,richtung(X,Z,H1)\r\n"
					+ "                  ,Z \\= H2.\r\n"
					+ "\r\n"
					+ "haltenHauptAuffahren3(X) :- hauptStrasse(H1, H2)\r\n"
					+ "                  ,richtung(_,H1,H2)\r\n"
					+ "                  ,zwischen1h(H1,H2, Dz1)\r\n"
					+ "                  ,!\r\n"
					+ "                  ,richtung(X,Z,H1)\r\n"
					+ "                  ,member(Z,Dz1).\r\n"
					+ "\r\n"
					+ "haltenHauptAuffahren4(X) :- hauptStrasse(H1, H2)\r\n"
					+ "                  ,richtung(_,H2,H1)\r\n"
					+ "                  ,zwischen1h(H2,H1, Dz1)\r\n"
					+ "                  ,!\r\n"
					+ "                  ,richtung(X,Z,H2)\r\n"
					+ "                  ,member(Z,Dz1).\r\n"
					+ "\r\n"
					+ "haltenHauptAuffahren(X) :- haltenHauptAuffahren1(X); haltenHauptAuffahren2(X); haltenHauptAuffahren3(X); haltenHauptAuffahren4(X)."
					+ "\r\n"
					+ "haltenWegenHauptAbbiegen1(X):-  hauptStrasse(H1, _)\r\n"
					+ "                             ,richtung(_,H1, A1)\r\n"
					+ "                             ,zwischen1h(H1,A1, Dz1), zwischen2N(H1,A1, Dz2)\r\n"
					+ "                             ,!\r\n"
					+ "                             ,richtung(X,Z,A)\r\n"
					+ "                             ,memberOr(Z,A,Dz1)\r\n"
					+ "                             ,memberOr(Z,A,Dz2).\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "haltenWegenHauptAbbiegen2(X):-  hauptStrasse(_, H2)\r\n"
					+ "                             ,richtung(_,H2, A1)\r\n"
					+ "                             ,zwischen1h(H2,A1, Dz1), zwischen2N(H2,A1, Dz2)\r\n"
					+ "                             ,!\r\n"
					+ "                             ,richtung(X,Z,A)\r\n"
					+ "                             ,memberOr(Z,A,Dz1)\r\n"
					+ "                             ,memberOr(Z,A,Dz2).\r\n"
					+ "\r\n"
					+ "\r\n"
					+"haltenWegenHauptAbbiegen(X) :- haltenWegenHauptAbbiegen1(X); haltenWegenHauptAbbiegen2(X).\r\n"
					+ "\r\n"
					+ "haltenh(X):- haltenHauptQueren(X); haltenHauptAuffahren(X); haltenWegenHauptAbbiegen(X);haltenRvL(X).\r\n"
					+ "halten(X):- haltenBeiWenden(X). \r\n"
					+ "halten(X):- nichtEntlangHauptStrasse(X), haltenh(X).\r\n";
		
		for(int i = 0; i < autos.size(); i++) {
			prolog += ":- ( halten(a"+ i +") -> write(true) ; write(false) ), nl."+ "\r\n" ;
		}
		
			prolog += ":- halt.";
		}
		
		else {

			for(int i = 0; i < autos.size(); i++) {
				prolog += "richtung(a" +i +"," + autos.get(i).getZufahrt() +","+ autos.get(i).getAbfahrt() + "). \r\n";
			}
			
			for(int i= 0; i < anzahlStrassen; i++) {
				prolog += "rechtsVon("+ alph.charAt(i)+"," + alph.charAt((i+1)%anzahlStrassen) ;
				prolog += ")." + "\r\n";
			}
			prolog += "\r\n ";
			
			prolog += "member(X, [X|_]).\r\n"
					+"member(X, [_|T]):- member(X,T).\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "zwischen1h(X,X, []).\r\n"
					+ "zwischen1h(X,Y, []):- rechtsVon(X,Y).\r\n"
					+ "zwischen1h(X,Y, [Hz|Tz]):- rechtsVon(Hz, Y), zwischen1h(X, Hz, Tz).\r\n"
					+ "\r\n"
			
					+ "zwischen2h(X,Y, []):- rechtsVon(Y,X).\r\n"
					+ "zwischen2h(X,Y, [Hz|Tz]):- rechtsVon(Y, Hz), zwischen2h(X, Hz, Tz).\r\n"
					+ "\r\n"
			
					+ "zwischen2(X,Y,[X|[Y|Z]]):- zwischen2h(X,Y,Z).\r\n";
			
			prolog += "haltenRvL(X):- richtung(X,Z,A), zwischen1h(Z,A, Dz1), zwischen2(Z,A, Dz2)\r\n"
					+ "          ,!\r\n"			
					+ "          ,richtung(_,Z1, A1)\r\n"
					+ "          ,member(Z1, Dz1)\r\n"
					+ "          ,member(A1, Dz2)."
					+ "\r\n"
					+ "haltenBeiWenden(X) :- richtung(X,Z,Z), richtung(A1,_,Z), A1\\= X.\r\n"
					+ "\r\n";
			
			prolog += "halten(X):- haltenBeiWenden(X).\r\n"
					+ "halten(X):-haltenRvL(X)." + "\r\n";
			
			for(int i = 0; i < autos.size(); i++) {
				prolog += ":- ( halten(a"+ i +") -> write(true) ; write(false) ), nl."+ "\r\n" ;
			}
			
				prolog += ":- halt.";
		}
		return prolog;
	}
	
	public static void createPLFile(String prolog, String path) {
		Path filePath = Path.of(path);
		
		 try {
	            // Create or replace the file and write the content
	            Files.writeString(
	                filePath,
	                prolog,
	                StandardOpenOption.CREATE,      // create the file if it doesn't exist
	                StandardOpenOption.TRUNCATE_EXISTING  // overwrite the file if it exists
	            );
	            System.out.println("File written successfully.");
	        } catch (IOException e) {
	            System.err.println("Error writing to file: " + e.getMessage());
	        }
	}
	
}
