/**
 * 
 */
package test;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ReverendDread
 * Oct 30, 2019
 */
@Slf4j
public class NPCStatsDumper {

	public static void main(String[] args) throws Exception {
	
		Document doc = Jsoup.connect("https://oldschool.runescape.wiki/w/Man").get();
		
		log.info(doc.title());
		
		Elements content = doc.getElementsByClass("rsw-infobox infobox plainlinks infobox-switch no-parenthesis-style infobox-monster");
		
		if (!content.isEmpty()) {	
			Element table = content.select("table").get(0);
			Elements rows = table.select("tr");
			for (Element row : rows) {
				Elements values = row.select("td");
				for (Element value : values) {
					if (value.hasAttr("data-attr-param")) {
						log.info("{} - {}", value.attr("data-attr-param"), value.text());
					}
				}
			}
		}
		
	}
	
}
