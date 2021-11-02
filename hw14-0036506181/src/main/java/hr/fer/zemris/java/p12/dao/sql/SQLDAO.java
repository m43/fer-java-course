package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * This is an implementation of the subsystem DAO that uses SQL technology. This
 * concrete implementation expects to find a connection for SQL queries using
 * the {@link SQLConnectionProvider} class, which is why up to the point of
 * using this class the connection should already have been set. In web
 * applications, a typical solution is to configure a filter that intercepts the
 * servlet calls and before that insert a connection from the connection poll
 * here and after the processing finishes it is removed.
 * 
 * @author Frano Rajič
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						Poll poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						polls.add(poll);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Couldnt get the list of polls.", ex);
		}
		return polls;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if (rs != null && rs.next()) {
						poll = new Poll();
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while getting the poll with given id " + id + ".", ex);
		}
		return poll;
	}

	@Override
	public List<PollOption> getPollOptions(long pollID) {
		List<PollOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();

		try {
			PreparedStatement ps = con.prepareStatement(
					"SELECT id, pollID, optionTitle, optionLink, votesCount from PollOptions where pollID = ? order by id");
			ps.setLong(1, pollID);
			try {
				ResultSet rs = ps.executeQuery();
				try {
					while (rs != null && rs.next()) {
						PollOption option = new PollOption();
						option.setId(rs.getLong(1));
						option.setPollId(rs.getLong(2));
						option.setTitle(rs.getString(3));
						option.setLink(rs.getString(4));
						option.setNumberOfVotes(rs.getLong(5));
						options.add(option);
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignoreable) {
					}
				}
			} finally {
				try {
					ps.close();
				} catch (Exception ignoreable) {
				}
			}

		} catch (Exception ex) {
			throw new DAOException("Error while getting the poll options of poll with given id " + pollID + ".", ex);
		}

		return options;
	}

	@Override
	public void addVote(long pollID, long id) {
		Connection con = SQLConnectionProvider.getConnection();

		ResultSet rs;
		try {
			rs = con.prepareStatement(
					"SELECT DISTINCT votesCount FROM PollOptions WHERE pollID = " + pollID + " AND id =" + id)
					.executeQuery();
			if (rs == null || !rs.next()) {
				throw new DAOException("Votes could not be found in PollOptions for given id = " + id
						+ " and given pollID = " + pollID);
			}

			long votes = rs.getLong(1);
			try {
				rs.close();
			} catch (Exception ignoreable) {
			}

			PreparedStatement ps = con
					.prepareStatement("UPDATE PollOptions SET votesCount = ? where pollID = ? AND id = ?");
			ps.setLong(1, votes + 1);

			ps.setLong(2, pollID);
			ps.setLong(3, id);

			try {
				int updatedRows = ps.executeUpdate();
				if (updatedRows != 1) {
					throw new DAOException("Nothing was updated when trying to update vote count");
				}
			} finally {
				try {
					ps.close();
				} catch (Exception ignoreable) {
				}
			}
		} catch (Exception e) {
			throw new DAOException("Couldn't add vote.");
		}
	}

}