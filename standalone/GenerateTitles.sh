#!/bin/bash
# Run the jar, generates out.json with the DUMP
java -jar web_scraper-0.1.0-SNAPSHOT-standalone.jar
# Format newline with sed
sed 's/\\n/\n/g' out.json > out.txt
# Set col width and then output it all to a file
fold -w 120 -s out.txt > titles_descriptions.txt
# Clean up the intermediate files
rm out.json
rm out.txt
