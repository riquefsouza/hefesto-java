package br.com.hfs.util;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import jakarta.inject.Named;

import org.apache.commons.lang3.time.DateUtils;

@Named
public final class DateUtil implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant DATA_PADRAO. */
	public static final String DATE_STANDARD = "dd/MM/yyyy";

	/** The Constant DATA_HORA_PADRAO. */
	public static final String DATE_TIME_STANDARD = "dd/MM/yyyy HH:mm:ss";

	public static final String DATE_TIME_AMERICAN = "yyyy-MM-dd HH:mm:ss";
	
	/** The Constant DATA_NUMERO_PADRAO. */
	public static final String DATE_NUMBER_STANDARD = "yyyyMMdd";

	/** The Constant DATA_NUMERO_HORA_PADRAO. */
	public static final String DATE_NUMBER_TIME_STANDARD = "yyyyMMddHHmmss";

	/** The Constant DATA_MES_ANO. */
	public static final String DATE_MONTH_YEAR = "MMM/yyyy";
	
	/** The meses. */
	public static String[] months = { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto",
			"Setembro", "Outubro", "Novembro", "Dezembro" };

	public static String[] months3letters = { "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" };
	
	public String[] getMonths() {
		return months;
	}

	public static String getMonthFromDate(Date data){
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		int month = cal.get(Calendar.MONTH);
		return months[month];
	}

	public static Date getDateFromMonth(Date data, String smes){
		int nmes = -1;
		
		if (smes!=null && !smes.isEmpty()) {
			for (int i = 0; i < months.length; i++) {
				if (months[i].equals(smes)){
					nmes = i;
					break;
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			cal.set(Calendar.MONTH, nmes);
			return cal.getTime();
		} else
			return null;
	}
	
	public String getDateStandard() {
		return DATE_STANDARD;
	}

	public String getDateTimeStandard() {
		return DATE_TIME_STANDARD;
	}

	public String getDateMonthYear() {
		return DATE_MONTH_YEAR;
	}

	public static List<Integer> getListYear() {
		List<Integer> listaAno = new ArrayList<Integer>();
		for (int i = 2017; i <= 2100; i++) {
			listaAno.add(i);
		}
		return listaAno;
	}

	public static List<String> getListYearString() {
		List<String> listaAno = new ArrayList<String>();
		for (int i = 1990; i <= 2100; i++) {
			listaAno.add(Integer.toString(i));
		}
		return listaAno;
	}
	
	public static String Format(Date Data, String Padrao) {
		if (Data == null) {
			return "";
		}
		if (Padrao.isEmpty()) {
			Padrao = DATE_STANDARD;
		}
		SimpleDateFormat formatD = new SimpleDateFormat(Padrao, new Locale("pt","BR"));
		return formatD.format(Data).toLowerCase();
	}
	
	public String format(Date Data) {
		return Format(Data, "");
	}

	public String formatDT(Date Data) {
		return Format(Data, DATE_TIME_STANDARD);
	}
	
	public static Long toLong(Date Data, String standard) {
		if (Data == null) {
			return null;
		}
		if (standard.isEmpty()) {
			standard = DATE_NUMBER_STANDARD;
		}
		SimpleDateFormat formatD = new SimpleDateFormat(standard);
		String sdata = formatD.format(Data).toLowerCase();
		return Long.parseLong(sdata);
	}

	/**
	 * To date.
	 *
	 * @param Data
	 *            the data
	 * @param standard
	 *            the padrao
	 * @return the date
	 */
	public static Date toDate(String Data, String standard) {
		Date DataAux = null;
		SimpleDateFormat FormataDT = new SimpleDateFormat(standard);
		try {
			DataAux = FormataDT.parse(Data);
		} catch (ParseException E) {
			return null;
		}
		return DataAux;
	}

	public static Date toDate(int day, int month, int year) {
		Calendar dataR = Calendar.getInstance();
		dataR.set(5, day);
		dataR.set(2, month - 1);
		dataR.set(1, year);
		dataR.set(11, 0);
		dataR.set(12, 0);
		dataR.set(13, 0);
		dataR.set(14, 0);

		return dataR.getTime();
	}

	public static Date conferenciaHoras(Date dia) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dia);
		cal.add(10, 8);
		dia = cal.getTime();
		dia = retirarHoras(dia);
		return dia;
	}

	/**
	 * Retirar horas.
	 *
	 * @param data
	 *            the data
	 * @return the date
	 */
	public static Date retirarHoras(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		int dia = calendar.get(5);
		int mes = calendar.get(2);
		int ano = calendar.get(1);

		Calendar dataR = Calendar.getInstance();
		dataR.set(5, dia);
		dataR.set(2, mes);
		dataR.set(1, ano);
		dataR.set(11, 0);
		dataR.set(12, 0);
		dataR.set(13, 0);
		dataR.set(14, 0);
		return dataR.getTime();
	}

	/**
	 * Ultima hora dia.
	 *
	 * @param data
	 *            the data
	 * @return the date
	 */
	public static Date ultimaHoraDia(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		int dia = calendar.get(5);
		int mes = calendar.get(2);
		int ano = calendar.get(1);

		Calendar dataR = Calendar.getInstance();
		dataR.set(5, dia);
		dataR.set(2, mes);
		dataR.set(1, ano);
		dataR.set(11, 23);
		dataR.set(12, 59);
		dataR.set(13, 59);
		dataR.set(14, 0);
		return dataR.getTime();
	}

	/**
	 * Converter.
	 *
	 * @param data
	 *            the data
	 * @param formatoOrigem
	 *            the formato origem
	 * @param formatoDestino
	 *            the formato destino
	 * @return the string
	 */
	public static String converter(String data, String formatoOrigem, String formatoDestino) {
		return Format(toDate(data, formatoOrigem), formatoDestino);
	}

	/**
	 * Checks if is data valida.
	 *
	 * @param data
	 *            the data
	 * @return true, if is data valida
	 */
	public static boolean isDataValida(String data) {
		String[] vet = data.split("/");
		if (vet.length == 3) {
			return isDataValida(vet[0], vet[1], vet[2]);
		}
		return false;
	}

	/**
	 * Checks if is data valida.
	 *
	 * @param dia
	 *            the dia
	 * @param mes
	 *            the mes
	 * @param ano
	 *            the ano
	 * @return true, if is data valida
	 */
	public static boolean isDataValida(String dia, String mes, String ano) {
		int vDia = 0;
		int vMes = 0;
		int vAno = 0;
		try {
			vDia = Integer.valueOf(dia).intValue();
			vMes = Integer.valueOf(mes).intValue();
			vAno = Integer.valueOf(ano).intValue();
		} catch (Exception e) {
			return false;
		}
		boolean bissexto = vAno % 4 == 0;
		boolean resp = false;
		if ((vAno <= 0) || (vAno > 2100)) {
			return resp;
		}
		if ((vMes < 1) || (vMes > 12)) {
			return resp;
		}
		switch (vMes) {
		case 2:
			if ((!bissexto) || (vMes != 2) || (vDia <= 29)) {
				if ((bissexto) || (vMes != 2) || (vDia <= 29)) {
					resp = true;
				}
			}
			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (vDia <= 31) {
				resp = true;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (vDia <= 30) {
				resp = true;
			}
			break;
		}
		return resp;
	}

	/**
	 * Adicionar.
	 *
	 * @param pDataE
	 *            the data E
	 * @param pQtd
	 *            the qtd
	 * @param strTipo
	 *            the str tipo
	 * @return the date
	 */
	public static Date Adicionar(Date pDataE, int pQtd, String strTipo) {
		Calendar dataR = Calendar.getInstance();
		dataR.setTime(pDataE);
		if (strTipo.equalsIgnoreCase("D")) {
			dataR.add(5, pQtd);
			return retirarHoras(dataR.getTime());
		}
		if (strTipo.equalsIgnoreCase("M")) {
			dataR.add(2, pQtd);
			return retirarHoras(dataR.getTime());
		}
		if (strTipo.equalsIgnoreCase("A")) {
			dataR.add(1, pQtd);
			return retirarHoras(dataR.getTime());
		}
		if (strTipo.equalsIgnoreCase("H")) {
			dataR.add(10, pQtd);
			return dataR.getTime();
		}
		return null;
	}

	/**
	 * Gets the trimestre.
	 *
	 * @param data
	 *            the data
	 * @return the trimestre
	 */
	public static int getTrimestre(Date data) {
		Calendar aux = Calendar.getInstance();
		aux.setTime(data);
		switch (aux.get(2)) {
		case 0:
		case 1:
		case 2:
			return 1;
		case 3:
		case 4:
		case 5:
			return 2;
		case 6:
		case 7:
		case 8:
			return 3;
		case 9:
		case 10:
		case 11:
			return 4;
		}
		return 0;
	}

	/**
	 * Gets the ultimo dia mes.
	 *
	 * @param mes
	 *            the mes
	 * @param ano
	 *            the ano
	 * @return the ultimo dia mes
	 */
	public static int getUltimoDiaMes(int mes, int ano) {
		int dia = 0;
		if ((mes == 1) || (mes == 3) || (mes == 5) || (mes == 7) || (mes == 8) || (mes == 10) || (mes == 12)) {
			dia = 31;
		} else if ((mes == 4) || (mes == 6) || (mes == 9) || (mes == 11)) {
			dia = 30;
		} else if (mes == 2) {
			if (ano % 4 != 0) {
				dia = 28;
			} else if (ano % 100 != 0) {
				dia = 29;
			} else if (ano % 400 != 0) {
				dia = 28;
			} else {
				dia = 29;
			}
		} else {
			dia = 0;
		}
		return dia;
	}

	/**
	 * Calcula diferenca absoluta entre datas.
	 *
	 * @param dataInicial
	 *            the data inicial
	 * @param dataFinal
	 *            the data final
	 * @return the int
	 */
	public static long calculaDiferencaAbsolutaEntreDatas(String dataInicial, String dataFinal) {
		return calculaDiferencaAbsolutaEntreDatas(toDate(dataInicial, "dd/MM/yyyy"), toDate(dataFinal, "dd/MM/yyyy"));
	}

	/**
	 * Calcula diferenca absoluta entre datas.
	 *
	 * @param dataInicial
	 *            the data inicial
	 * @param dataFinal
	 *            the data final
	 * @return the int
	 */
	public static long calculaDiferencaAbsolutaEntreDatas(Date dataInicial, Date dataFinal) {
		long diff = calculaDiferencaEntreDatas(dataInicial, dataFinal);
		if (diff < 0) {
			diff *= -1;
		}
		return diff;
	}

	public static long calculaDiferencaEntreDatas(Date dataInicial, Date dataFinal) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(retirarHoras(dataInicial));

		Calendar c2 = Calendar.getInstance();
		c2.setTime(retirarHoras(dataFinal));

		LocalDate ld1 = LocalDateTime.ofInstant(c1.toInstant(), c1.getTimeZone().toZoneId()).toLocalDate();
		LocalDate ld2 = LocalDateTime.ofInstant(c2.toInstant(), c2.getTimeZone().toZoneId()).toLocalDate();
		
		long diff = ChronoUnit.DAYS.between(ld2, ld1);
		
		//int diff = Days.daysBetween(new LocalDate(c2.getTimeInMillis()), new LocalDate(c1.getTimeInMillis())).getDays();
		return diff;
	}

	/**
	 * Calcula diferenca entre datas.
	 *
	 * @param dataInicial
	 *            the data inicial
	 * @param dataFinal
	 *            the data final
	 * @return the int
	 */
	public static long calculaDiferencaEntreDatas(String dataInicial, String dataFinal) {
		return calculaDiferencaEntreDatas(toDate(dataInicial, "dd/MM/yyyy"), toDate(dataFinal, "dd/MM/yyyy"));
	}

	/**
	 * Calcula diferenca entre datas em meses.
	 *
	 * @param dataInicial
	 *            the data inicial
	 * @param dataFinal
	 *            the data final
	 * @return the int
	 */
	public static int calculaDiferencaEntreDatasEmMeses(Date dataInicial, Date dataFinal) {
		int mesIni = Integer.parseInt(Format(dataInicial, "MM"));
		int anoIni = Integer.parseInt(Format(dataInicial, "yyyy"));
		int mesFim = Integer.parseInt(Format(dataFinal, "MM"));
		int anoFim = Integer.parseInt(Format(dataFinal, "yyyy"));

		return (anoFim - anoIni) * 12 - -(mesFim - mesIni);
	}

	/**
	 * Retornar diferenca entre datas em anos.
	 *
	 * @param dataInicial
	 *            the data inicial
	 * @param dataFinal
	 *            the data final
	 * @return the int
	 */
	public static int retornarDiferencaEntreDatasEmAnos(String dataInicial, String dataFinal) {
		String anoInicial = dataInicial.substring(4, dataInicial.length());
		String anoFinal = dataFinal.substring(4, dataFinal.length());
		return Integer.parseInt(anoFinal) - Integer.parseInt(anoInicial);
	}

	/**
	 * Retornar data adicionada dias.
	 *
	 * @param data
	 *            the data
	 * @param dias
	 *            the dias
	 * @return the date
	 */
	public static Date retornarDataAdicionadaDias(Date data, int dias) {
		if (data == null) {
			return null;
		}
		Calendar dataAux = Calendar.getInstance();
		dataAux.setTime(data);
		dataAux.add(5, dias);
		return dataAux.getTime();
	}

	/**
	 * Retornar data subtraida dias.
	 *
	 * @param data
	 *            the data
	 * @param dias
	 *            the dias
	 * @return the date
	 */
	public static Date retornarDataSubtraidaDias(Date data, int dias) {
		if (data == null) {
			return null;
		}
		Calendar dataAux = Calendar.getInstance();
		dataAux.setTime(data);
		dataAux.add(5, dias * -1);
		return dataAux.getTime();
	}

	/**
	 * Diferenca entre datas em horas.
	 *
	 * @param dataInicial
	 *            the data inicial
	 * @param dataFinal
	 *            the data final
	 * @return the double
	 */
	public static double diferencaEntreDatasEmHoras(Date dataInicial, Date dataFinal) {
		long diferenca = dataFinal.getTime() - dataInicial.getTime();

		double horas = diferenca / 1000.0D / 60.0D / 60.0D;
		return horas;
	}

	/**
	 * Gets the mes.
	 *
	 * @param mes
	 *            the mes
	 * @return the mes
	 */
	public static int getMes(String mes) {
		for (int i = 0; i < months.length; i++) {
			if (months[i].toUpperCase().indexOf(mes.toUpperCase()) > -1) {
				return i + 1;
			}
		}
		return -1;
	}

	/**
	 * Gets the mes.
	 *
	 * @param mes
	 *            the mes
	 * @return the mes
	 */
	public static String getMes(int mes) {
		for (int i = 0; i <= months.length; i++) {
			if (i == mes) {
				return months[(mes - 1)];
			}
		}
		return "";
	}

	/**
	 * Gets the ultimo diado mes.
	 *
	 * @param data
	 *            the data
	 * @return the ultimo diado mes
	 */
	public static Date getUltimoDiadoMes(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);		
		int dia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, dia);
		return ultimaHoraDia(calendar.getTime());
	}

	/**
	 * Tratar data nula.
	 *
	 * @param dataFinal
	 *            the data final
	 * @return the date
	 */
	public static Date tratarDataNula(Date dataFinal) {
		return dataFinal == null ? toDate("31/12/2199", "dd/MM/yyyy") : dataFinal;
	}

	/**
	 * Validar conflito periodo.
	 *
	 * @param newDtInicial
	 *            the new dt inicial
	 * @param newDtFinal
	 *            the new dt final
	 * @param oldDtInicial
	 *            the old dt inicial
	 * @param oldDtFinal
	 *            the old dt final
	 * @return true, if successful
	 */
	public static boolean validarConflitoPeriodo(Date newDtInicial, Date newDtFinal, Date oldDtInicial,
			Date oldDtFinal) {
		Date newDtFimOk = tratarDataNula(newDtFinal);
		Date oldDtFinalOk = tratarDataNula(oldDtFinal);
		if ((newDtInicial.compareTo(oldDtFinalOk) <= 0) && (newDtFimOk.compareTo(oldDtInicial) >= 0)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if is fim de semana.
	 *
	 * @param data
	 *            the data
	 * @return true, if is fim de semana
	 */
	public static boolean isFimDeSemana(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		int diaSemana = calendar.get(7);
		if ((diaSemana == 7) || (diaSemana == 1)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is fim de semana between.
	 *
	 * @param dataInicio
	 *            the data inicio
	 * @param dataFim
	 *            the data fim
	 * @return true, if is fim de semana between
	 */
	public static boolean isFimDeSemanaBetween(Date dataInicio, Date dataFim) {
		Date dt = retirarHoras(dataInicio);
		Date dtFim = retirarHoras(dataFim);
		while (dt.compareTo(dtFim) <= 0) {
			if (isFimDeSemana(dt)) {
				return true;
			}
			dt = Adicionar(dt, 1, "D");
		}
		return false;
	}

	/**
	 * Gets the primeiro dia do mes.
	 *
	 * @param date
	 *            the date
	 * @return the primeiro dia do mes
	 */
	public static Date getPrimeiroDiaDoMes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(5, 1);
		return retirarHoras(calendar.getTime());
	}

	/**
	 * Valida hora.
	 *
	 * @param hora
	 *            the hora
	 * @return true, if successful
	 */
	public static boolean validaHora(String hora) {
		if (hora.length() != 5) {
			return false;
		}
		int hr = Integer.valueOf(hora.substring(0, hora.indexOf(":"))).intValue();
		if ((hr < 0) || (hr >= 24)) {
			return false;
		}
		int min = Integer.valueOf(hora.substring(hora.indexOf(":") + 1, hora.length())).intValue();
		if ((min < 0) || (min >= 60)) {
			return false;
		}
		return true;
	}

	/**
	 * Verifica data em intervalo.
	 *
	 * @param dataParaVerificar
	 *            the data para verificar
	 * @param dataInicioIntervalo
	 *            the data inicio intervalo
	 * @param dataFimIntervalo
	 *            the data fim intervalo
	 * @return true, if successful
	 */
	public static boolean verificaDataEmIntervalo(Date dataParaVerificar, Date dataInicioIntervalo,
			Date dataFimIntervalo) {
		if ((dataParaVerificar == null) || (dataInicioIntervalo == null)) {
			return false;
		}
		if (dataFimIntervalo == null) {
			if (dataParaVerificar.compareTo(dataInicioIntervalo) >= 0) {
				return true;
			}
		} else if ((dataParaVerificar.compareTo(dataFimIntervalo) <= 0)
				&& (dataParaVerificar.compareTo(dataInicioIntervalo) >= 0)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the primeiro diado ano.
	 *
	 * @param ano
	 *            the ano
	 * @return the primeiro diado ano
	 */
	public static Date getPrimeiroDiadoAno(int ano) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(ano, Calendar.JANUARY, 1, 0, 0, 0);
		return calendar.getTime();
	}

	/**
	 * Gets the ultimo diado ano.
	 *
	 * @param ano
	 *            the ano
	 * @return the ultimo diado ano
	 */
	public static Date getUltimoDiadoAno(int ano) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(ano, 11, 31);
		return calendar.getTime();
	}

	/**
	 * Checks if is mesmo dia.
	 *
	 * @param d1
	 *            the d 1
	 * @param d2
	 *            the d 2
	 * @return true, if is mesmo dia
	 */
	public static boolean isMesmoDia(Date d1, Date d2) {
		return DateUtils.isSameDay(d1, d2);
	}

	/**
	 * Checks if is between.
	 *
	 * @param data
	 *            the data
	 * @param dataInicio
	 *            the data inicio
	 * @param dataFim
	 *            the data fim
	 * @return true, if is between
	 */
	public static boolean isBetween(Date data, Date dataInicio, Date dataFim) {
		return (retirarHoras(data).compareTo(retirarHoras(dataInicio)) >= 0)
				&& (retirarHoras(data).compareTo(retirarHoras(dataFim)) <= 0);
	}

	/**
	 * Gets the maior data.
	 *
	 * @param d1
	 *            the d 1
	 * @param d2
	 *            the d 2
	 * @return the maior data
	 */
	public static Date getMaiorData(Date d1, Date d2) {
		long dif = calculaDiferencaEntreDatas(d1, d2);
		if (dif >= 0) {
			return d1;
		}
		return d2;
	}

	/**
	 * Data por extenso.
	 *
	 * @param data
	 *            the data
	 * @return the string
	 */
	public static String dataPorExtenso(Date data) {
		return new SimpleDateFormat("dd").format(data) + " de " + new SimpleDateFormat("MMMM").format(data) + " de "
				+ new SimpleDateFormat("yyyy").format(data);
	}

	/**
	 * Gets the ano data.
	 *
	 * @param data
	 *            the data
	 * @return the ano data
	 */
	public static Integer getAnoData(Date data) {
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(data);
		return Integer.valueOf(c.get(1));
	}

	/**
	 * Gets the mes data.
	 *
	 * @param data
	 *            the data
	 * @return the mes data
	 */
	public static Integer getMesData(Date data) {
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(data);		
		return Integer.valueOf(c.get(Calendar.MONTH));
	}
	
	/**
	 * Gets the dia data.
	 *
	 * @param data
	 *            the data
	 * @return the dia data
	 */
	public static Integer getDiaData(Date data) {
		Calendar c = GregorianCalendar.getInstance();
		c.setTime(data);		
		return Integer.valueOf(c.get(Calendar.DAY_OF_MONTH));
	}	

	/**
	 * Sets the ultimo dia do mes.
	 *
	 * @param campo the campo
	 * @return the date
	 */
	public static Date setUltimoDiaDoMes(Date campo){
		Calendar c = Calendar.getInstance();
		c.setTime(campo);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}
	
	public static Date getDataSemHora() {
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.set(dataAtual.get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		dataAtual.set(Calendar.MILLISECOND, 0);
		return dataAtual.getTime();
	}

	public static List<DateMonthYear> montaDataMesAno() {
		List<DateMonthYear> lista = new ArrayList<DateMonthYear>();
		
		Calendar cal = Calendar.getInstance();
		//int year = cal.get(Calendar.YEAR)-10;
		int year = 2018;
		
		for (int ano = year; ano <= 2100; ano++) {
			for (int mes = 0; mes < months.length; mes++) {
				cal.set(ano, mes, 1, 0, 0, 0);
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				Date data = cal.getTime();
				DateMonthYear dma = new DateMonthYear(months[mes] + "/" + ano, mes+1, ano, data);
				lista.add(dma);
			}
		}
		return lista;
	}
	
	public static List<String> montaMesAno() {
		List<String> lista = new ArrayList<String>();
		
		Calendar cal = Calendar.getInstance();
		//int year = cal.get(Calendar.YEAR)-10;
		int year = 2018;
		
		for (int ano = year; ano <= 2100; ano++) {
			for (int mes = 0; mes < months.length; mes++) {
				cal.set(ano, mes, 1, 0, 0, 0);
				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				lista.add(months[mes] + "/" + ano);
			}
		}
		return lista;		
	}
	
	public static DateMonthYear getDataMesAnoFromId(String id){
		if (id!=null && !id.isEmpty()) {
			String sano = id.substring(0, 4);
			String smes = id.substring(4);
			
			int ano = Integer.parseInt(sano);
			int mes = Integer.parseInt(smes)-1;

			Calendar cal = Calendar.getInstance();
			cal.set(ano, mes, 1, 0, 0, 0);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date data = cal.getTime();
			DateMonthYear dma = new DateMonthYear(months[mes] + "/" + ano, mes+1, ano, data);
			
			return dma;
		}
		return null;
	}
	
	public static Date getDateFromDataMesAno(String mesAno){
		if (mesAno!=null && !mesAno.isEmpty()) {
			String[] parte = mesAno.split("/");
			int nmes = 0;
			int nano = Integer.parseInt(parte[1]);
			
			for (int mes = 0; mes < months.length; mes++) {
				if (parte[0].equals(months[mes])){
					nmes = mes;
					break;
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.set(nano, nmes, 1, 0, 0, 0);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
	 
			return cal.getTime();
		}
		return new Date();
	}
	
	public static String getDataMesAnoFromDate(Date data){
		if (data!=null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			int ano = cal.get(Calendar.YEAR);
			int mes = cal.get(Calendar.MONTH);
			return months[mes] + "/" + ano;
		}		
		return "";
	}

	public static String getDataMes3LetrasAnoFromDate(Date data){
		if (data!=null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			int ano = cal.get(Calendar.YEAR);
			int mes = cal.get(Calendar.MONTH);
			return months3letters[mes].toLowerCase() + "/" + ano;
		}		
		return "";
	}
	
	
}
