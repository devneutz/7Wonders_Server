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

/**
 * 
 * @author Gabriel de Castilho, Joel Neutzner
 * 
 *         Diese Klasse erstellt die Tabelle Player in der mysql DB. In dieser
 *         Klasse werden zudem alle �berpr�fungen welche gegen die
 *         Datenbankinhalte durchgef�hrt werden m�ssen abgebildet.
 *
 */
public class DbHelper {

	private static final Logger logger = Logger.getLogger("");

	/*
	 * In dieser Methode wird die Datenbank initialisiert. Hierbei wird die Tabelle
	 * "player" erstellt.
	 */
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
		} catch (SQLException inEx) {
			logger.log(Level.SEVERE, "Error creating table [player]", inEx);
		} finally {
		}
	}

	/*
	 * In dieser Methode wird gepr�ft ob das Passwort des �bergebenen Spielers auch
	 * mit dem Spielerpasswort welches in der DB abgelegt ist �bereinstimmt.
	 */
	public static boolean isPasswordValid(IPlayer inPlayer) {
		try {
			Connection tmpConnection = DbConnectionPool.getInstance().getConnection();
			String tmpCheckQuery = "SELECT password FROM player WHERE nickname = ?";
			PreparedStatement tmpCheckStatement = tmpConnection.prepareStatement(tmpCheckQuery,
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			;
			tmpCheckStatement.setString(1, inPlayer.getName());
			ResultSet tmpResult = tmpCheckStatement.executeQuery();
			if (tmpResult.next()) {
				String tmpPassword = tmpResult.getString("password");
				return inPlayer.getPassword().equalsIgnoreCase(tmpPassword);
			} else {
				return false;
			}
		} catch (SQLException inEx) {
			logger.log(Level.WARNING, "Error trying to validate password!", inEx);
		} finally {
			// TODO Cleanup?
		}
		return false;
	}

	/*
	 * In dieser Methode wird �berpr�ft ob der �bergebene Spieler bereits in der DB
	 * vorhanden ist.
	 */
	public static boolean doesPlayerExist(IPlayer inPlayer) {
		try {

			Connection tmpConnection = DbConnectionPool.getInstance().getConnection();
			String tmpCheckQuery = "SELECT nickname FROM player WHERE nickname = ?";
			PreparedStatement tmpCheckStatement = tmpConnection.prepareStatement(tmpCheckQuery,
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			tmpCheckStatement.setString(1, inPlayer.getName());
			ResultSet tmpResult = tmpCheckStatement.executeQuery();
			if (tmpResult.next()) {
				String tmpNickname = tmpResult.getString("nickname");
				return inPlayer.getName().equalsIgnoreCase(tmpNickname);
			} else {
				return false;
			}
		} catch (SQLException inEx) {
			logger.log(Level.WARNING, "Error trying to validate existence of player!", inEx);
		} finally {
			// TODO Cleanup?
		}
		return false;

	}

	/*
	 * In dieser Methode wird der �bergebene Spieler in die Datenbank eingef�gt.
	 */
	public static void addPlayer(IPlayer inPlayer) {
		try {
			Connection tmpConnection = DbConnectionPool.getInstance().getConnection();
			String tmpCheckQuery = "INSERT INTO player (nickname, password, NoOfGames, NoOfVictories, NoOfThirdPlace, NoOfSecondPlace)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement tmpCheckStatement = tmpConnection.prepareStatement(tmpCheckQuery,
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			;
			tmpCheckStatement.setString(1, inPlayer.getName());
			tmpCheckStatement.setString(2, inPlayer.getPassword());
			tmpCheckStatement.setInt(3, 0);
			tmpCheckStatement.setInt(4, 0);
			tmpCheckStatement.setInt(5, 0);
			tmpCheckStatement.setInt(6, 0);

			tmpCheckStatement.execute();

		} catch (SQLException inEx) {
			logger.log(Level.WARNING, "Error trying to add player to db!", inEx);
		} finally {
			// TODO Cleanup?
		}

	}
}
