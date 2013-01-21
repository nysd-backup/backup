UPDATE 
	TESTA e 
set
	e.attr = :c_TARGET_TEST_1,
	e.attr2 = :attr2set
where 
	1 = 1 
--% if( $test == c_TARGET_TEST_1)
	and e.test = :c_TARGET_TEST_1
	and e.test = :test
--% end
--% if( $test != c_TARGET_TEST_1 && $attr && attr == c_TARGET_TEST_1_OK)
	and e.attr = :attr
--% end
--% if( $arc > c_TARGET_DECIMAL )
	--% if( c_TARGET_BOOL && $arc == c_TARGET_INT )
		and e.attr2 = :c_TARGET_INT
	--% end	
--% end	