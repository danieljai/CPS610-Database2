#!/usr/bin/env python3
import sys
tmp_word = None
total_count = 0
for line in sys.stdin:
	line = line.strip()
	word, count = line.split('\t')
	count = int(count)
	if tmp_word == word:
		total_count += count
	else:
		# print '%s\t%s' % (tmp_word, total_count)
		print ('%s\t%s' % (tmp_word, total_count))
		total_count = count
		tmp_word = word

# print '%s\t%s' % (tmp_word, total_count)
print ('%s\t%s' % (tmp_word, total_count))
