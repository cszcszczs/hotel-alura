package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.modelo.Reserva;

public class ReservaDAO {
	private Connection connection;
	
	public ReservaDAO (Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Reserva reserva) {
		try {
			String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, formaPago) VALUES (?, ?, ?, ?,)";
			
			try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				
				pstm.setDate(1, reserva.getFechaE());
				pstm.setDate(2, reserva.getFechaS());
				pstm.setString(3, reserva.getValor());
				pstm.setString(4, reserva.getFormaPago());
				
				pstm.execute();
				
				try(ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> buscar() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas";
			
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();
				
				transformarResultSetEnReserva(reservas, pstm);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> buscarId(String id) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_pago FROM reservas WHERE id = ?";
			
			try(PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, id);
				pstm.execute();
				
				transformarResultSetEnReserva(reservas, pstm);	
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} 
	}
	
	public void eliminar(Integer id) {
		try(PreparedStatement pstm = connection.prepareStatement(
				"DELETE FROM reservar WHERE id = ?")) {
			pstm.setInt(1, id);
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void actualizar(Date fechaE, Date fechaS, String valor, String formaPago, Integer id) {
		try (PreparedStatement pstm = connection.prepareStatement(
				"UPDATE reservas SET fecha_entrada = ?, fecha_salida = ?, "
				+ "valor = ?, formaPago = ? WHERE id = ?"
				)) {
			pstm.setDate(1, fechaE);
			pstm.setDate(2, fechaS);
			pstm.setString(3, valor);
			pstm.setString(4, formaPago);
			pstm.setInt(5, id);	
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void transformarResultSetEnReserva(List<Reserva> reservas, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Reserva producto = new Reserva(rst.getInt(1), rst.getDate(2), rst.getDate(3), rst.getString(4), rst.getString(5));
				
				reservas.add(producto);
			}
		}
	}
}
