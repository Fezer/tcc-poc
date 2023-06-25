package br.ufpr.estagio.modulo.dto;

import java.util.Date;

public class PeriodoRecessoDTO {

	private long id;
	private Date dataInicio;
	private Date dataFim;
	private TermoDeRescisaoDTO termoRescisao;
	
	public PeriodoRecessoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PeriodoRecessoDTO(long id, Date dataInicio, Date dataFim, TermoDeRescisaoDTO termoRescisao) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.termoRescisao = termoRescisao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public TermoDeRescisaoDTO getTermoRescisao() {
		return termoRescisao;
	}

	public void setTermoRescisao(TermoDeRescisaoDTO termoRescisao) {
		this.termoRescisao = termoRescisao;
	}
	
}
