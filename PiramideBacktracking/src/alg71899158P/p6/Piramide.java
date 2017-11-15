package alg71899158P.p6;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Piramide {

	private static final String FICHERO = "files/caso16.txt";
	private String[][] elementos;
	private String[][] piramideGenerada;
	private int[][] piramide;
	private int[][] constantes;
	private int[][] colores;
	private int[][] aleatorios;
	private int altura;
	
	private boolean haySolucion=false;
	

	public static void main(String[] args) {
		Piramide p = new Piramide();

	}
	
	
	public Piramide(){
		cargarElementos();
		System.out.println(ToString());
		copiaPiramide();
		copiaConstantes();
		copiaColores();
		backtracking(0,0);
		
	
		//generarCaso(4,1,5);
	}
	


	private void cargarElementos(){
		String linea = " ";
		String[] datosPiramide = null;
		boolean isFirst=true;
		int i=0;
		try {
			BufferedReader fichero = new BufferedReader(new FileReader(FICHERO));
			while (fichero.ready()) {
				linea = fichero.readLine();
				datosPiramide = linea.split(" ");
				if(isFirst){
					//Si es la primera linea asigna la altura de la pirámide
					isFirst=false;
					altura= Integer.parseInt(linea);
					elementos = new String[altura][altura];
				}else{
					for(int j=0;j<datosPiramide.length;j++){
						elementos[i][j]=datosPiramide[j];
					}
					i++;
				}
			}
			fichero.close();
		} catch (FileNotFoundException fnfe) {
			System.out.println("El archivo no se ha encontrado.");
		} catch (IOException ioe) {
			new RuntimeException("Error de entrada/salida.");
		}
	
	}
		
		private String ToString(){
			String cad="";
			int nuevaAltura = altura;
			for(int i=0;i<elementos.length;i++){
				for(int k =0; k<nuevaAltura;k++){
					cad+=" ";
				}
				nuevaAltura--;
				for(int j=0;j<elementos.length;j++){
					if(elementos[i][j]!=null){
						cad+=elementos[i][j] + " ";
						
					}
			}
			cad+="\n";
		}
			return cad;
		}
		
		
		private boolean esValidoSumaOResta(int padre,int hijo1,int hijo2){
			int suma=hijo1+hijo2;
			int resta=Math.abs(hijo1-hijo2);
			if(suma==padre || resta==padre){				
				return true;
			}
			return false;
		}
		
		
	public void backtracking(int fila, int columna) {
		if (fila == altura) {
			haySolucion = true;
			mostrarPirmide();
		} else {
			int newFila = siguienteFila(fila, columna);
			int newColumna = siguienteColumna(fila, columna);
			for (int i = 1; i < 10 && !haySolucion; i++) {
				piramide[fila][columna] = i;
				if (esValido(fila, columna)) {
					backtracking(newFila, newColumna);
				}

			}
		}
	}
	
	private boolean esValido(int fila, int columna) {
		int valor = piramide[fila][columna];
		
		if(colores[fila][columna]!=-1 ){
			int valorColor = buscaColor(fila,columna);
			if (valorColor != valor) {
				return false;
			}
		}
		
		if(constantes[fila][columna]!=-1) {
			if (constantes[fila][columna]!=valor){
				return false;
			}
		}
						
		if (fila == 0 || columna == 0) {
			return true;
		} else {
			return esValidoSumaOResta(piramide[fila - 1][columna - 1],
					piramide[fila][columna - 1],
					valor);
		}
	}
				

	private int buscaColor(int fila, int columna) {

		int color = colores[fila][columna];

		for (int i = 0; i < altura; i++) {
			for (int j = 0; j < altura; j++) {
				if (colores[i][j] == color) {
					return piramide[i][j];
				}
			}
		}
		
		throw new RuntimeException();
	}

	private void mostrarPirmide() {
		String cad = "";
		int nuevaAltura = altura;
		for (int i = 0; i < piramide.length; i++) {
			for (int k = 0; k < nuevaAltura; k++) {
				cad += " ";
			}
			nuevaAltura--;
			for (int j = 0; j < piramide.length; j++) {
				if (piramide[i][j] != 0) {
					cad += piramide[i][j] + " ";

				}
			}
			cad += "\n";
		}
		System.out.println(cad);
	}

	private int siguienteColumna(int fila, int columna) {
		if(columna==fila){
			return 0;
		}else{
			columna++;
			return columna;
		}
	}

	private int siguienteFila(int fila,int columna) {
		if(fila==columna){
			fila++;
			return fila;
		}else{
			return fila;
		}
	}
	
	private void copiaPiramide() {
		String str;
		piramide = new int[altura][altura];
		for (int i = 0; i < elementos.length; i++) {
			for (int j = 0; j < elementos.length; j++) {
				str = elementos[i][j];
				if (str != null) {
					switch (str) {
					case "*":
						piramide[i][j] = 0; // Hueco libre
						break;

					case "B":
						piramide[i][j] = 0; // Azul
						break;

					case "R":
						piramide[i][j] = 0; // Rojo
						break;

					case "Y":
						piramide[i][j] = 0; // Amarillo
						break;

					default:
						piramide[i][j] = 0;//Integer.valueOf(elementos[i][j]);
						break;

					}
				}
			}
		}
	}
	
	private void copiaConstantes() {
		String str;
		constantes = new int[altura][altura];
		for (int i = 0; i < elementos.length; i++) {
			for (int j = 0; j < elementos.length; j++) {
				str = elementos[i][j];
				if (str != null) {
					switch (str) {
					case "*":
						constantes[i][j] = -1; 
						break;
						
					case "B":
						constantes[i][j] = -1; 
						break;

					case "R":
						constantes[i][j] = -1; 
						break;

					case "Y":
						constantes[i][j] = -1; 
						break;

					default:
						constantes[i][j] =Integer.valueOf(elementos[i][j]);
						break;

					}
				}
			}
		}
	}
	
	private void copiaColores() {
		String str;
		colores = new int[altura][altura];
		for (int i = 0; i < elementos.length; i++) {
			for (int j = 0; j < elementos.length; j++) {
				str = elementos[i][j];
				if (str != null) {
					switch (str) {
					case "B":
						colores[i][j] = 1; // Azul
						break;

					case "R":
						colores[i][j] = 2; // Rojo
						break;

					case "Y":
						colores[i][j] = 3; // Amarillo
						break;

					default:
						colores[i][j] = -1; //Otra cosa
						break;

					}
				}
			}
		}
	}
	
	//,int huecos,int colores,String fichero
	private void generarCaso(int alt,int numColores,int numHuecos) {
		altura=alt;
		piramide = new int[altura][altura];
		piramideGenerada = new String[altura][altura];
		
		colores=new int[altura][altura];
		for(int i=0;i<colores.length;i++){
			for(int j=0;j<colores.length;j++){
				colores[i][j]=-1;
			}
		}
		
		ArrayList<Posicion> posiciones= new ArrayList<Posicion>();
		
		for(int i=0;i<altura;i++){
			for(int j=0;j<=i;j++){
				posiciones.add(new Posicion(i,j));
			}
		}
		
		Random r = new Random();
		for(int color=1;color<=numColores;color++){
			//Primera posicion
			int indice1=r.nextInt(posiciones.size()-1);
			Posicion posicion1 = posiciones.get(indice1);
			posiciones.remove(indice1);
			//Segunda posicion
			int indice2=r.nextInt(posiciones.size()-1);
			Posicion posicion2 = posiciones.get(indice2);
			posiciones.remove(indice2);
			
			piramide[posicion1.getFila()][posicion1.getColumna()]=color;
			piramide[posicion2.getFila()][posicion2.getColumna()]=color;
			colores[posicion1.getFila()][posicion1.getColumna()]=color;
			colores[posicion2.getFila()][posicion2.getColumna()]=color;
		}
		
		mostrarPirmide();		
		backtracking(0,0);
		

		
		for(int hueco=0;hueco<numHuecos;hueco++){
			//Hueco libe
			int indice=r.nextInt(posiciones.size()-1);
			Posicion posicion = posiciones.get(indice);
			posiciones.remove(indice);
			
			piramide[posicion.getFila()][posicion.getColumna()]=-2;	
		}		
		mostrarPirmide();
		
		transformarPiramide();
		mostrarPiramideGnerada();
		
     
		String ruta = "files/piramideAltura"+altura+"Colores"+numColores+"Huecos"+numHuecos+".txt";
    	File fichero = new File(ruta);
		
		try{
		FileWriter fw = new FileWriter(ruta);


    	fw.write(altura+"\n");
        for (int i = 0; i < piramideGenerada.length; i++){
        	for(int j=0;j<i;j++){
        		fw.write(piramideGenerada[i][j]);
        		}
        	fw.write("\n");
        }
		}catch(Exception e){
			e.printStackTrace();
		}
	}

                         

	
	
	private void mostrarPiramideGnerada() {
		String cad = "";
		int nuevaAltura = altura;
		for (int i = 0; i < piramideGenerada.length; i++) {
			for (int k = 0; k < nuevaAltura; k++) {
				cad += " ";
			}
			nuevaAltura--;
			for (int j = 0; j <= i; j++) {
					cad += piramideGenerada[i][j] + " ";
			}
			cad += "\n";
		}
		System.out.println(cad);
	}




	private void transformarPiramide() {
		piramideGenerada=new String[altura][altura];
		for(int i=0;i<piramide.length;i++){
			for(int j=0;j<piramide.length;j++){
				int color =colores[i][j];
				switch(color){
				
				case 1:
					piramideGenerada[i][j]="B"; //BLUE
					break;
					
				case 2:
					piramideGenerada[i][j]="R"; //RED
					break;
					
				case 3:
					piramideGenerada[i][j]="Y"; //YELLOW
					break;
					
				default:
					if(piramide[i][j]==-2){
						piramideGenerada[i][j]="*";
					}else{
						piramideGenerada[i][j]=""+piramide[i][j];
					}
					break;
					
				}												
			}
		}
		
	}



	private void mostrarAletorio() {
		String cad = "";
		int nuevaAltura = altura;
		for (int i = 0; i < aleatorios.length; i++) {
			for (int k = 0; k < nuevaAltura; k++) {
				cad += " ";
			}
			nuevaAltura--;
			for (int j = 0; j < aleatorios.length; j++) {
				if (aleatorios[i][j] != 0) {
					cad += aleatorios[i][j] + " ";

				}
			}
			cad += "\n";
		}
		System.out.println(cad);
	}


	
	}
	

