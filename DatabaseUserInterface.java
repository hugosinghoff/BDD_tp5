
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * This is a skeleton for realizing a very simple database user interface in java. 
 * The interface is an Applet, and it implements the interface ActionListener. 
 * If the user performs an action (for example he presses a button), the procedure actionPerformed 
 * is called. Depending on his actions, one can implement the database connection (disconnection), 
 * querying or insert. 
 * 
 * @author zmiklos
 *
 */
public class DatabaseUserInterface extends java.applet.Applet implements ActionListener {

 TextArea mRes;
 private Button etudiant, parcours, prof, cours, matiere, salle, connect, insert, query, disconnect, requete7, confirmer;
 private TextField mStat, m1, m2, m3, m4, m5;
 private char action, table;
 private static final long serialVersionUID = 1L; 
 // JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 static final String DB_URL = "jdbc:mysql://mysql.istic.univ-rennes1.fr/base_17002220";

 //  Database credentials
 static final String USER = "user_17002220";
 static final String PASS = "bdd123";
 
 private Connection con;
 private Statement stat;
 /**
  * This procedure is called when the Applet is initialized.
  * 
  */
 public void init ()
 {   
	 action = ' ';
	 table = ' ';
	 /**
	  * Definition of text fields
	  */
     mStat = new TextField(150);
     mStat.setEditable(false);
     m1 = new TextField(150);
     m2 = new TextField(150);
     m3 = new TextField(150);
     m4 = new TextField(150);
     m5 = new TextField(150);
     mRes = new TextArea(10,150);
     mRes.setEditable(false);
    
     /**
      * First we define the buttons, then we add to the Applet, finally add and ActionListener 
      * (with a self-reference) to capture the user actions.  
      */
     connect = new Button("CONNECT");
     insert = new Button("INSERT");
     query = new Button("QUERY");
     disconnect = new Button("DISCONNECT");
     requete7 = new Button("REQUETE7");
     etudiant = new Button("ETUDIANT");
     parcours = new Button("PARCOURS");
     prof = new Button("PROF");
     cours = new Button("COURS");
     matiere = new Button("MATIERE");
     salle = new Button("SALLE");
     confirmer = new Button("CONFIRMER");
     
     connect.addActionListener(this);
     insert.addActionListener(this);
     query.addActionListener(this);
     disconnect.addActionListener(this);
     requete7.addActionListener(this);
     etudiant.addActionListener(this);
     parcours.addActionListener(this);
     prof.addActionListener(this);
     cours.addActionListener(this);
     matiere.addActionListener(this);
     salle.addActionListener(this);
     confirmer.addActionListener(this);
     
     add(connect);
     add(insert);
     add(query);
     add(disconnect);
     add(requete7);    

     add(etudiant) ;
     add(parcours) ;
     add(prof) ;
     add(cours);
     add(matiere);
     add(salle);
     
     add(mStat);
     
     
     add(m1);
     add(m2);
     add(m3);
     add(m4);
     add(m5);
     add(mRes);
     add(confirmer);

     mRes.setText("Query results");
     
    
     setStatus("Waiting for user actions.");
 }
 
 void cleanTextField()
 {
	 m1.setText(" ");
	 m2.setText(" ");
	 m3.setText(" ");
	 m4.setText(" ");
	 m5.setText(" ");
 }
 
 
 /**
  * This procedure is called upon a user action.
  * 
  *  @param event The user event. 
  */
  public void actionPerformed(ActionEvent event)
 {
	 // Extract the relevant information from the action (i.e. which button is pressed?)
	 Object cause = event.getSource();
	 // Act depending on the user action
	 // Button CONNECT
     if (cause == connect)
     {
    	 connectToDatabase();
     }
     if (cause == insert)
     {
    	action = 'i';
     }
     if (cause == query)
     {
    	m5.setText("select * from Prof;");
     	action = 'q';
     }
     if (cause == disconnect)
     {
    	 disconnectFromDatabase();
     }
     /*--------*/
     if (cause == etudiant)
     {
    	 m1.setText("Nom");
    	 m2.setText("Num Parcours");
		 table = 'e';
     }
     if (cause == parcours)
     {
    	 m1.setText("Nom Parcours");
		 table = 'p';
     }
     if (cause == prof)
     {
    	 m1.setText("Nom Prof");
    	 m2.setText("Num Matiere");
		 table = 'f';
     }
     if (cause == cours)
     {
    	 m1.setText("id Salle");
    	 m2.setText("id Matiere");
    	 m3.setText("Plage horaire (c'est toujours 2h donc ex : 8)");
    	 m4.setText("idProf");
    	 m5.setText("idParcours");
		 table = 'c';
     }
     if (cause == matiere)
     {
    	 m1.setText("Nom");
		 table = 'm';
     }
     if (cause == salle)
     {
    	 m1.setText("Type");
		 table = 's';
     }
     if(cause == requete7)
     {
    	 queryQ1(); 
     }
     if(cause == confirmer)
     {
    	 if(action == 'i') // insert
    	 {
    		 if(table == 'e')
    		 {
    			 insertEtudiant();
    			 cleanTextField();
    		 }
    		 else if(table == 'p')
    		 {
    			 insertParcours();
    			 cleanTextField();
    		 }
    		 else if(table == 'f')
    		 {
    			 insertProf();
    			 cleanTextField();
    		 }
    		 else if(table == 'c')
    		 {
    			 insertCours();
    			 cleanTextField();
    		 }
    		 else if(table == 'm')
    		 {
    			 insertMatiere();
    			 cleanTextField();
    		 }
    		 else if(table == 's')
    		 {
    			 insertSalle();
    			 cleanTextField();
    		 }
    		 table = ' ';
    		 action = ' ';
    	 }
    	 else if(action == 'q') // query
    	 {
    		 if(table == 'e')
    		 {
    			 queryEtudiant();
    			 cleanTextField();
    		 }
    		 else if(table == 'p')
    		 {
    			 queryParcours();
    			 cleanTextField();
    		 }
    		 else if(table == 'f')
    		 {
    			 queryProf();
    			 cleanTextField();

    		 }
    		 else if(table == 'c')
    		 {
    			 queryCours();
    			 cleanTextField();
    		 }
    		 else if(table == 'm')
    		 {
    			 queryMatiere();
    			 cleanTextField();

    		 }
    		 else if(table == 's')
    		 {
    			 querySalle();
    			 cleanTextField();
    		 }
    		 else
    		 {
    			 query();
    			 cleanTextField();
    		 }
    		 table = ' ';
    		 action = ' ';
    	 }
     }
 }

/**
 * Set the status text. 
 * 
 * @param text The text to set. 
 */
private void setStatus(String text){
	    mStat.setText("Status: " + text);
  }

/**
 * Procedure, where the database connection should be implemented. 
 */
private void connectToDatabase(){
	try{
	    Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(DB_URL, USER, PASS);
	    stat = con.createStatement();
		setStatus("Connected to the database");
	} catch(Exception e){
		System.err.println(e.getMessage());
		setStatus("Connection failed");
	}
}


/**
 * Procedure, where the database connection should be implemented. 
 */
private void disconnectFromDatabase(){
	try{
		stat.close();
	    con.close();
	    setStatus("Disconnected from the database");
	} catch(Exception e){
		System.err.println(e.getMessage());
		setStatus("Disconnection failed");
	}
}

/**
 * Insert tuples to the database. 
 */
private void insertEtudiant(){
	try{
		
		String name = m1.getText();
		String parcours = m2.getText();
		setStatus("Inserting --( " + name + ", " + parcours + ")-- to the database");
	    String sql = "INSERT INTO Etudiant(Nom_Etudiant, Parcours_idParcours) VALUES (\""+name+"\",\""+parcours+"\");"; // parcours en liste déroulante ici
	    stat.executeUpdate(sql);
		setStatus("Insertion succeded");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}
}

/**
 * Insert tuples to the database. 
 */
private void insertMatiere(){
	try{
		
		String name = m1.getText();
		setStatus("Inserting --( " + name + ")-- to the database");
	    String sql = "INSERT INTO Matiere(Nom_Matiere) VALUES (\""+name+"\");"; // parcours en liste déroulante ici
	    stat.executeUpdate(sql);
		setStatus("Insertion succeded");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}
}

private void insertParcours(){
	try{
		
		String name = m1.getText();
		setStatus("Inserting --( " + name + ")-- to the database");
	    String sql = "INSERT INTO Parcours(Nom_Parcours) VALUES (\""+name+"\");"; // parcours en liste déroulante ici
	    stat.executeUpdate(sql);
		setStatus("Insertion succeded");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}
}

private void insertSalle(){
	try{
		
		String name = m1.getText();
		setStatus("Inserting --( " + name + ")-- to the database");
	    String sql = "INSERT INTO Salle(Type_Salle) VALUES (\""+name+"\");"; // parcours en liste déroulante ici
	    stat.executeUpdate(sql);
		setStatus("Insertion succeded");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}
}

private void insertProf(){
	try{
		String name = m1.getText();
		String matiere = m2.getText();
		setStatus("Inserting --( " + name + ")-- to the database");
	    String sql = "INSERT INTO Prof(Nom_Prof) VALUES (\""+name+"\");"; // parcours en liste déroulante ici
	    stat.executeUpdate(sql);
	    
	    //recuperer l'id du dernier insert dans prof càa le max (HUGO BOSS)
	    String sql3 = "select max(idProf) from Prof;";
	    ResultSet rs = stat.executeQuery(sql3);
	    rs.next();
	    int tmp = rs.getInt("max(idProf)");
	    String sql2 = "INSERT INTO Prof_has_Matiere(Prof_idProf, Matiere_idMatiere) VALUES ("+tmp+",+"+matiere+");"; // parcours en liste déroulante ici
	    stat.executeUpdate(sql2);
		setStatus("Insertion succeded");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}
}

private void insertCours(){
	try{
		String salle = m1.getText();
		String matiere = m2.getText();
		String plage = m3.getText();
		String idProf = m4.getText();
		String Parcours = m5.getText();

		setStatus("Inserting --(Cours)-- to the database");
	    String sql = "INSERT INTO Cours(idSalle, idMatiere, plage_Horaire, Prof_idProf) VALUES (\""+salle+"\",\""+matiere+"\",\""+plage+"\",\""+idProf+"\");"; // parcours en liste déroulante ici
	    stat.executeUpdate(sql);
	    
	    //recuperer l'id du dernier insert dans prof càa le max (HUGO BOSS)
	    String sql3 = "select max(idCours) from Cours;";
	    ResultSet rs = stat.executeQuery(sql3);
	    rs.next();
	    int tmp = rs.getInt("max(idCours)");
	    String sql2 = "INSERT INTO Cours_has_Parcours(Cours_idCours, Parcours_idParcours) VALUES ("+tmp+",+"+Parcours+");"; // parcours en liste déroulante ici
	    stat.executeUpdate(sql2);
		setStatus("Insertion succeded");
		} catch(Exception e){
			System.err.println(e.getMessage());
			setStatus("Insertion failed");
		}
}


private void queryParcours(){
	setStatus("Querying the database");
	String query = "select * from Parcours;";
    try {
    	ResultSet rs = stat.executeQuery(query);
		mRes.setText("");
		while(rs.next()){
	         //Retrieve by column name
	         int id  = rs.getInt("idParcours");
	         String nom = rs.getString("Nom_Parcours");

	         //Display values
	         mRes.append("ID: " + id);
	         mRes.append(", nom: " + nom+"\n");
	      }
	} catch (SQLException e) {
		System.err.println(e.getMessage());
		setStatus("Query failed");
	}
}

private void query()
{
	setStatus("Querying the database");
	String query = m5.getText();
	try {
    	ResultSet rs = stat.executeQuery(query);
    	ResultSetMetaData metadata = rs.getMetaData(); 
    	
    	String[] noms = new String[metadata.getColumnCount()]; // nom de chaque colonne
    	String[] typeJava = new String[noms.length]; //type de chaque colonne
    	for(int i = 0; i < noms.length; i++){ 
    	      String nomColonne = metadata.getColumnName(i+1); 
    	      noms[i] = nomColonne; 
    	      typeJava[i] = metadata.getColumnClassName(i+1);
    	} 
    	
		mRes.setText("");
		while(rs.next()){
	         //Retrieve by column name
	    	for(int i = 0; i < noms.length; i++){ 
	    		if(typeJava[i] == "java.lang.Integer")
	    		{
	    			int id  = rs.getInt(noms[i]);
	    			mRes.append(noms[i]+" : "+ id+ " ");
	    		}
	    		else if(typeJava[i] == "java.lang.String")
	    		{
	   	            String nom = rs.getString(noms[i]);
	    			mRes.append(noms[i]+" : "+ nom+ " ");
	    		}
	    	}
			mRes.append("\n");
	      }
	} catch (SQLException e) {
		System.err.println(e.getMessage());
		setStatus("Query failed");
	}
}

private void queryEtudiant(){
	setStatus("Querying the database");
	String query = "select * from Etudiant;";
    try {
    	ResultSet rs = stat.executeQuery(query);
		mRes.setText("");
		while(rs.next()){
	         //Retrieve by column name
	         int id = rs.getInt("idEtudiant");
	         String nom = rs.getString("Nom_Etudiant");
	         int idParcours  = rs.getInt("Parcours_idParcours");

	         //Display values
	         mRes.append("ID: " + id);
	         mRes.append(", nom: " + nom);
	    	 mRes.append(", idParcours: " + idParcours+"\n");
	      }
	} catch (SQLException e) {
		System.err.println(e.getMessage());
		setStatus("Query failed");
	}
}

private void queryMatiere(){
	setStatus("Querying the database");
	String query = "select * from Matiere;";
    try {
    	ResultSet rs = stat.executeQuery(query);
		mRes.setText("");
		while(rs.next()){
	         //Retrieve by column name
	         int id = rs.getInt("idMatiere");
	         String nom = rs.getString("Nom_Matiere");
	         //Display values
	         mRes.append("ID: " + id);
	         mRes.append(", nom: " + nom+"\n");
	      }
	} catch (SQLException e) {
		System.err.println(e.getMessage());
		setStatus("Query failed");
	}
}

private void querySalle(){
	setStatus("Querying the database");
	String query = "select * from Salle;";
    try {
    	ResultSet rs = stat.executeQuery(query);
		mRes.setText("");
		while(rs.next()){
	         //Retrieve by column name
	         int id = rs.getInt("idSalle");
	         String nom = rs.getString("Type_Salle");
	         //Display values
	         mRes.append("ID: " + id);
	         mRes.append(", nom: " + nom+"\n");
	      }
	} catch (SQLException e) {
		System.err.println(e.getMessage());
		setStatus("Query failed");
	}
}

private void queryProf(){
	setStatus("Querying the database");
	String query = "select * from Prof;";
    try {
    	ResultSet rs = stat.executeQuery(query);
		mRes.setText("");
		while(rs.next()){
	         //Retrieve by column name
	         int id = rs.getInt("idProf");
	         String nom = rs.getString("Nom_Prof");
	         //Display values
	         mRes.append("ID: " + id);
	         mRes.append(", nom: " + nom+"\n");
	      }
	} catch (SQLException e) {
		System.err.println(e.getMessage());
		setStatus("Query failed");
	}
}

private void queryCours(){
	setStatus("Querying the database");
	String query = "select * from Cours;";
    try {
    	ResultSet rs = stat.executeQuery(query);
		mRes.setText("");
		while(rs.next()){
	         //Retrieve by column name
	         int idSalle = rs.getInt("idSalle");
	         int idMatiere = rs.getInt("idMatiere");
	         int plage_Horaire = rs.getInt("plage_Horaire");
	         int Prof_idProf = rs.getInt("Prof_idProf");
	         //Display values
	         mRes.append("ID Salle: " + idSalle);
	         mRes.append(", ID Matiere: " + idMatiere);
	         mRes.append(", ID plage_horaire: " + plage_Horaire);
	         mRes.append(", ID Prof_idProf: " + Prof_idProf);
	      }
	} catch (SQLException e) {
		System.err.println(e.getMessage());
		setStatus("Query failed");
	}
}



private void queryQ1(){
	setStatus("On fait la requête : Trouve le nom des enseignants pour un cours spécique ");
	String query = "SELECT Nom_Prof, idCours, Nom_Matiere FROM Cours NATURAL JOIN Matiere NATURAL JOIN Prof_has_Matiere NATURAL JOIN Prof WHERE Nom_Matiere = \"Math\";";
	m5.setText(query);
}

}
