function buildAbcXml(id,name,value) {
	var xmlData = 
	"<abc>" +
	"<id>__id__</id>"+
	"<name>__name__</name>"+
	"<value>__value__</value>"+
	"</abc>";
	xmlData = xmlData.replace('__id__', id);
	xmlData = xmlData.replace('__name__', name);
	xmlData = xmlData.replace('__value__', value);
	return(xmlData);
}

function buildAbcXml(name,value) {
	var xmlData = 
	"<abc>" +
	"<name>__name__</name>"+
	"<value>__value__</value>"+
	"</abc>";
	xmlData = xmlData.replace('__name__', name);
	xmlData = xmlData.replace('__value__', value);
	return(xmlData);
}