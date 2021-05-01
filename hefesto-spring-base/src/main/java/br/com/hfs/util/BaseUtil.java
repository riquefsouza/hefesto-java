package br.com.hfs.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

import javax.inject.Named;
import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.ObjectUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseUtil.
 */
@Named
@SuppressWarnings("rawtypes")
public class BaseUtil implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static Date nvlDateAmerican(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return DateUtil.toDate(obj.toString(), DateUtil.DATE_TIME_AMERICAN);
		}
	}

	public static Date nvlDate(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return DateUtil.toDate(obj.toString(), DateUtil.DATE_TIME_STANDARD);
		}
	}

	public static Boolean nvlBoolean(Object obj, boolean valorPadrao) {
		if (obj == null) {
			return valorPadrao;
		} else {
			return Boolean.parseBoolean(obj.toString());
		}
	}
	
	public static Boolean nvlBoolean(Object obj) {
		return nvlBoolean(obj, false);
	}

	/**
	 * Nvl.
	 *
	 * @param obj
	 *            the obj
	 * @return the string
	 */
	public static String nvlStr(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	/**
	 * Nvl int.
	 *
	 * @param obj
	 *            the obj
	 * @return the integer
	 */
	public static Integer nvlInt(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return Integer.parseInt(obj.toString());
		}
	}

	/**
	 * Nvl long.
	 *
	 * @param obj
	 *            the obj
	 * @return the long
	 */
	public static Long nvlLong(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return Long.parseLong(obj.toString());
		}
	}

	/**
	 * Nvl big decimal.
	 *
	 * @param obj
	 *            the obj
	 * @return the big decimal
	 */
	public static BigDecimal nvlBigDecimal(Object obj) {
		if (obj == null) {
			return null;
		} else {
			return new BigDecimal(obj.toString());
		}
	}

	/**
	 * Rand int.
	 *
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the int
	 */
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	/**
	 * Rand big decimal.
	 *
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 * @return the big decimal
	 */
	public static BigDecimal randBigDecimal(int min, int max) {
		double valor = randInt(min, max * 100) * 0.001;
		BigDecimal bd = new BigDecimal(valor);
		return bd;
	}

	/**
	 * Checks if is negativo.
	 *
	 * @param valor
	 *            the valor
	 * @return true, if is negativo
	 */
	public boolean negativo(BigDecimal valor) {
		if (valor!=null)
			return (valor.compareTo(BigDecimal.ZERO) == -1);
		else
			return false;
	}

	/**
	 * Gets the padrao.
	 *
	 * @param <T>
	 *            the generic type
	 * @param o
	 *            the o
	 * @param padrao
	 *            the padrao
	 * @return the padrao
	 */
	public static <T> T getPadrao(T o, T padrao) {
		return o != null ? o : padrao;
	}

	/**
	 * Checks if is string empty.
	 *
	 * @param s
	 *            the s
	 * @return true, if is string empty
	 */
	public static boolean isStringEmpty(String s) {
		return (s == null) || (s.trim().isEmpty());
	}

	/**
	 * Compare.
	 *
	 * @param ignoreStringCase
	 *            the ignore string case
	 * @param lista
	 *            the lista
	 * @return the int
	 */
	@SuppressWarnings("unchecked")
	private static int compare(boolean ignoreStringCase, Comparable... lista) {
		if ((lista == null) || (lista.length == 0)) {
			return 0;
		}
		int comparacao = 0;
		for (int i = 0; i < lista.length; i += 2) {
			if ((ignoreStringCase) && ((lista[i] instanceof String)) && ((lista[(i + 1)] instanceof String))) {
				comparacao = ((String) lista[i]).compareToIgnoreCase((String) lista[(i + 1)]);
			} else {
				comparacao = ObjectUtils.compare(lista[i], lista[(i + 1)]);
			}
			if (comparacao != 0) {
				return comparacao;
			}
		}
		return comparacao;
	}

	/**
	 * Compare.
	 *
	 * @param lista
	 *            the lista
	 * @return the int
	 */
	public static int compare(Comparable... lista) {
		return compare(false, lista);
	}

	/**
	 * Compare ignore case.
	 *
	 * @param lista
	 *            the lista
	 * @return the int
	 */
	public static int compareIgnoreCase(Comparable... lista) {
		return compare(true, lista);
	}

	/**
	 * Aplicar mascara.
	 *
	 * @param texto
	 *            the texto
	 * @param mascara
	 *            the mascara
	 * @return the string
	 */
	public static String aplicarMascara(String texto, String mascara) {
		if ((!isStringEmpty(texto)) && (!isStringEmpty(mascara))) {
			try {
				MaskFormatter mf = new MaskFormatter(mascara);
				mf.setValueContainsLiteralCharacters(false);
				return mf.valueToString(texto);
			} catch (ParseException e) {
				return null;
			}
		}
		return texto;
	}

	public static <T, R> List<R> produce(Collection<T> collection, IBaseProducer<R, T> producer) {
		List<R> retorno = new ArrayList<R>();
		if ((collection != null) && (producer != null)) {
			for (T item : collection) {
				R produced = producer.apply(item);
				if (produced != null) {
					retorno.add(produced);
				}
			}
		}
		return retorno;
	}

	public static String formatCurrency(BigDecimal numero){
		Locale brasil = new Locale("pt", "BR");
		NumberFormat nf = NumberFormat.getCurrencyInstance(brasil);
		return nf.format(numero).replace(nf.getCurrency().getSymbol(), "").trim();
	}
	
	public static String formatInteger(long numero){
		Locale brasil = new Locale("pt", "BR");
		NumberFormat nf = NumberFormat.getCurrencyInstance(brasil);
		nf.setParseIntegerOnly(true);
		nf.setMaximumFractionDigits(0);		
		return nf.format(numero).replace(nf.getCurrency().getSymbol(), "").trim();
	}
	
	public static boolean containsNumericSequences(int min, int max, String stexto) {
		List<String> lista = new ArrayList<String>();
		String sValorMin = "";
		int vnum[] = new int[]{0,1,2,3,4,5,6,7,8,9};
		
		for (int n = 0; n < 7; n++) {
			for (int qtd = min-1; qtd <= max; qtd++) {
				sValorMin = "";
				for (int i = n; i <= (qtd+n); i++) {
					if (i <= max) {
						sValorMin += String.valueOf(vnum[i]);
					}
				}
				lista.add(sValorMin);
			}			
		}
		lista = lista.stream().distinct().collect(Collectors.toList());
		return lista.contains(stexto);
	}

	public static boolean containsConsecutiveIdenticalCharacters(int min, int max, String stexto) {
		List<String> lista = new ArrayList<String>();
		String sAlfaMin = "";
		
		for (char c = 'a'; c <= 'z'; c++) {
			for (int qtd = min; qtd <= max; qtd++) {
				sAlfaMin = "";
				for (int i = 1; i <= qtd; i++) {
					sAlfaMin += Character.toString(c);
				}
				lista.add(sAlfaMin);
				lista.add(sAlfaMin.toUpperCase());
			}
		}
		return lista.contains(stexto);
	}
	
}
