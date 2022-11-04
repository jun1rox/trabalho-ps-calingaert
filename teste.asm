inicio	copy zero older
		copy one old
		read limit
		write old
front 	load older
		add old
		store new
		sub limit
		brpos final
		write new
		copy old older
		copy new old
		br front
final		write limit
		stop
zero 		const 0
one 		const 1
older 	space
old		space
new		space
limit		space
copy zero older
END