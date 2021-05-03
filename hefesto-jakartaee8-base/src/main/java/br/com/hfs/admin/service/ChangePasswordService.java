package br.com.hfs.admin.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.hfs.util.BaseUtil;

public class ChangePasswordService {

	/*
	   As minimum requirements for user passwords, the following should be considered:
	   Minimum length of 8 characters;
	   Presence of at least 3 of the 4 character classes below:
	       uppercase characters;
	       lowercase characters;
	       numbers;
	       special characters;
	       Absence of strings (eg: 1234) or consecutive identical characters (yyyy);
	       Absence of any username identifier, such as: John Silva - user: john.silva - password cannot contain "john" or "silva".
	*/
	public boolean validatePassword(String login, String senha){
		if (senha.length() >= 8) {
			Pattern letterUppercase = Pattern.compile("[A-Z]");
			Pattern letterLowercase = Pattern.compile("[a-z]");
			Pattern digit = Pattern.compile("[0-9]");
			Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
	
			Matcher hasLetterUppercase = letterUppercase.matcher(senha);
			Matcher hasLetterLowercase = letterLowercase.matcher(senha);
			Matcher hasDigit = digit.matcher(senha);
			Matcher hasSpecial = special.matcher(senha);			
						
			boolean U = hasLetterUppercase.find();
			boolean L = hasLetterLowercase.find();
			boolean D = hasDigit.find();
			boolean S = hasSpecial.find();
			
			boolean hasChars = (U && L && D) || (S && U && L) || (D && S && U) || (L && D && S);
			
			return hasChars 
					&& !BaseUtil.containsNumericSequences(4,9, senha) 
					&& !BaseUtil.containsConsecutiveIdenticalCharacters(4,9, senha)
					&& !senha.contains(login);
	
		} else
			return false;
	}

}
