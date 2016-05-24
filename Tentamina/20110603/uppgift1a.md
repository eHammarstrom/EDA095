Genom att hämta ett tredjepartsbibliotek så som JSoup och sedan importera .jar filen till vår buildpath så kan vi använda biblioteket via en Java import. Genom att tillhandahålla JSoup med en URL kan man hämta ett dokument för att parse:a implicit, explicit parse görs med parse metoden där man tillför data som skall parse:as. Metoderna i JSoup kan användas direkt, statiskt.

För att använda det inbyggda htmleditorkit som är en standard i Java krävs bara en import och sedan kan man extenda htmleditorkit för att göra parse-metoden publik.
