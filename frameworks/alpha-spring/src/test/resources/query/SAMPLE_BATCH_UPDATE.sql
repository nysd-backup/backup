UPDATE 
	TESTA e 
set
	e.attr = :c_TARGET_TEST_1,
	e.attr2 = :attr2set
where 
	1 = 1 
	and e.test = :c_TARGET_TEST_1
	and e.attr = :attr
	or e.attr2 = :c_TARGET_INT
