package br.com.hfs.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VisitDirectoryUtil {

	private static VisitDirectoryUtil instance;

	private List<String> listDir;
	
	private List<File> listFile;

	private VisitDirectoryUtil() {
		listDir = new ArrayList<String>();
		listFile = new ArrayList<File>();
	}

	public static VisitDirectoryUtil getInstance() {
		if (instance == null) {
			instance = new VisitDirectoryUtil();
		}
		return instance;
	}

	public List<String> getListDirectory() {
		return listDir;
	}

	public List<File> getListFile() {
		return listFile;
	}

	public boolean ListDirectory(String sPath, final String sExtension) {
		listDir.clear();
		listFile.clear();
		visitAllDirectoriesFiles(new File(sPath));
		listDir.remove(0); // will always remove the first path
		listFile.remove(0);
		
		listDir = listDir.stream()
				.filter(item -> item.endsWith(sExtension))
				.collect(Collectors.toList());
		listFile = listFile.stream()
				.filter(item -> item.getAbsolutePath().endsWith(sExtension))
				.collect(Collectors.toList());
		
		return (listDir.size() > 0);
	}

	public boolean ListDirectory(String sPath) {
		listDir.clear();
		listFile.clear();
		visitAllDirectoriesFiles(new File(sPath));
		listDir.remove(0); // will always remove the first path
		listFile.remove(0);
		return (listDir.size() > 0);
	}

	private void processesAllDirectoriesFiles(File dir) {
		listFile.add(dir);
		listDir.add(dir.getAbsolutePath());
	}

	private void visitAllDirectoriesFiles(File dir) {
		processesAllDirectoriesFiles(dir);

		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				visitAllDirectoriesFiles(new File(dir, children[i]));
			}
		}
	}
	
}
