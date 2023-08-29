package jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import jdbc.dao.HuespedesDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;

public class HuespedesController {
	private HuespedesDAO huespedesDAO;
	
	public HuespedesController () {
		Connection connection = new ConnectionFactory().recuperarConexion();
		this.huespedesDAO = new HuespedesDAO(connection);
	}
	
	public void guardar (Huespedes huespedes) {
		this.huespedesDAO.guardar(huespedes);
	}
	
	public List<Huespedes> listarHuespedes() {
		return this.huespedesDAO.listarHuespedes();
	}
	
	public List<Huespedes> listarHuespedesId(String id) {
		return this.huespedesDAO.buscarId(id);
	}
	
	public void  actualizar(String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, Integer idReserva, Integer id) {
		this.huespedesDAO.actualizar(nombre, apellido, fechaN, nacionalidad, telefono, idReserva, id);
	}
	
	public void eliminar(Integer id) {
		this.huespedesDAO.eliminar(id);
	}

}
