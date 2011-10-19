select
	e 
from 
	TestEntity e 
where 
	1 = 1 
--% if( $test )
	and e.test = :test
--% end
--% if( $attr )
	and e.attr = :attr
--% end
--% if( $attr2 > 499.999 && ! $version && $arc == "500" )
	and e.version = '5'
--% end
order by e.test desc