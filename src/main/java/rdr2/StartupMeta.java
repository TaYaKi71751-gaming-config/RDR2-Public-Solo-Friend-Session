package rdr2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class StartupMeta {
	public String getString(String session_unique){
		char CR = 0x0d;
		char LF = 0x0a;
		String[] prefixes = {
"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
"<CDataFileMgr__ContentsOfDataFileXml>",
" <disabledFiles />",
" <includedXmlFiles itemType=\"CDataFileMgr__DataFileArray\" />",
" <includedDataFiles />",
" <dataFiles itemType=\"CDataFileMgr__DataFile\">",
"  <Item>",
"   <filename>platform:/data/cdimages/scaleform_platform_pc.rpf</filename>",
"   <fileType>RPF_FILE</fileType>",
"  </Item>",
"  <Item>",
"   <filename>platform:/data/ui/value_conversion.rpf</filename>",
"   <fileType>RPF_FILE</fileType>",
"  </Item>",
"  <Item>",
"   <filename>platform:/data/ui/widgets.rpf</filename>",
"   <fileType>RPF_FILE</fileType>",
"  </Item>",
"  <Item>",
"   <filename>platform:/textures/ui/ui_photo_stickers.rpf</filename>",
"   <fileType>RPF_FILE</fileType>",
"  </Item>",
"  <Item>",
"   <filename>platform:/textures/ui/ui_platform.rpf</filename>",
"   <fileType>RPF_FILE</fileType>",
"  </Item>",
"  <Item>",
"   <filename>platform:/data/ui/stylesCatalog</filename>",
"   <fileType>aWeaponizeDisputants</fileType> <!-- collision -->",
"  </Item>",
"  <Item>",
"   <filename>platform:/data/cdimages/scaleform_frontend.rpf</filename>",
"   <fileType>RPF_FILE_PRE_INSTALL</fileType>",
"  </Item>",
"  <Item>",
"   <filename>platform:/textures/ui/ui_startup_textures.rpf</filename>",
"   <fileType>RPF_FILE</fileType>",
"  </Item>",
"  <Item>",
"   <filename>platform:/data/ui/startup_data.rpf</filename>",
"   <fileType>RPF_FILE</fileType>",
"  </Item>",
"  <Item>",
"    <filename>platform:/boot_launcher_flow.#mt</filename>",
"    <fileType>STREAMING_FILE</fileType>",
"    <registerAs>boot_flow/boot_launcher_flow</registerAs>",
"    <overlay value=\"false\" />",
"    <patchFile value=\"false\" />",
"  </Item>",
" </dataFiles>",
" <contentChangeSets itemType=\"CDataFileMgr__ContentChangeSet\" />",
" <patchFiles />",
"</CDataFileMgr__ContentsOfDataFileXml>"
		};
		String session_unique_wrapped = "<!-- " + session_unique + " -->";
		String result = "";
		for(String prefix:prefixes){
			result += (prefix + CR);
		}
		if(session_unique.length() > 0) result += (session_unique_wrapped + CR + LF);
		return result;
	}
	public ArrayList<File> getPaths() throws IOException {
		Find find = new Find();
		ArrayList<File> play_rdr2_paths = find.findFile("PlayRDR2.exe");
		ArrayList<File> startup_meta_paths = new ArrayList<>();
		for(File play_rdr2_path:play_rdr2_paths){
			File rdr2_path = Path.getUpperPath(play_rdr2_path);
			File startup_meta_path = new File(String.join(File.separator,new String[]{rdr2_path + "","x64","data","startup.meta"}));
			if(Path.getUpperPath(startup_meta_path).exists()) {
				startup_meta_paths.add(startup_meta_path);
			}
		}
		return startup_meta_paths;
	}
	public void apply(String session_unique) throws IOException {
		StartupMeta startup_meta = new StartupMeta();
		String str = startup_meta.getString(session_unique);
		ArrayList<File> startup_meta_paths = startup_meta.getPaths();
		for(File startup_meta_path: startup_meta_paths){
			BufferedWriter writer = new BufferedWriter(new FileWriter(startup_meta_path));
			writer.write(str);
			writer.close();
		}
	}
	public void delete() throws IOException {
		StartupMeta startup_meta = new StartupMeta();
		ArrayList<File> startup_meta_paths = startup_meta.getPaths();
		for(File startup_meta_path: startup_meta_paths){
			startup_meta_path.delete();
		}
	}
}
// https://stackoverflow.com/questions/15464111/run-cmd-commands-through-java
