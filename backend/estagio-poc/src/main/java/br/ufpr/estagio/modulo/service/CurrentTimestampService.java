package br.ufpr.estagio.modulo.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CurrentTimestampService {
	
	private static final SimpleDateFormat dateFormater = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss");
	
	public CurrentTimestampService() {
		
	}

	public Timestamp getTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}
	
	public String formatarTimestamp(Timestamp timestamp) {
		return dateFormater.format(timestamp);
	}
}
