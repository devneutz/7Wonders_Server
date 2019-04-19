package ch.fhnw.sevenwonders.helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.fhnw.sevenwonders.database.DbConnectionPool;
import ch.fhnw.sevenwonders.interfaces.IPlayer;

public class DbHelper {
	
	private  static final Logger logger = Logger.getLogger("");
	 
	public static void InitDatabase() {
		try {
			Connection tmpConnection = DbConnectionPool.getInstance().getConnection();
			
			Statement tmpCreateTableStm = tmpConnection.createStatement();
			tmpCreateTableStm.execute("CREATE TABLE IF NOT EXISTS player"
					+ "					 ( nickname varchar(255) not null, "
					+ "						password varchar(255) not null, "
					+ "						NoOfGames int, "
					+ "						NoOfVictories int, "
					+ "						NoOfThirdPlace int,"
					+ "						NoOfSecondPlace int, "
					+ "						PRIMARY KEY(nickname) )");
		}catch(SQLException inEx) {
			logger.log(Level.SEVERE, "Error creating table [player]", inEx);
		}finally {
		}
	}
	
	public static boolean isPasswordValid(IPlayer inPlayer) {
		try {
			Connection tmpConnection = DbConnectionPool.getInstance().getConnection();
			String tmpCheckQuery = "SELECT password FROM player WHERE nickname = ?";
			PreparedStatement tmpCheckStatement = tmpConnection
											.prepareStatement(tmpCheckQuery, 
																ResultSet.TYPE_FORWARD_ONLY, 
																		ResultSet.CONCUR_READ_ONLY);;
			tmpCheckStatement.setString(1, inPlayer.getName());
			ResultSet tmpResult = tmpCheckStatement.executeQuery();
			if(tmpResult.next()) {
				String tmpPassword = tmpResult.getString("password");
				return inPlayer.getPassword() == tmpPassword;
			}else {
				return false;
			}
		}catch(SQLException inEx) {
			logger.log(Level.WARNING, "Error trying to validate password!", inEx);
		}finally {
			// TODO Cleanup?
		}
		return false;
	}
}
