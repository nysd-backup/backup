select /*+ HINT */
	*
from 
	TESTA
where 
	1 = 1 
--% if( $test )
	and test = :test
--% end
--% if( $attr )
	and attr = :attr
--% end
--% if( $attr2 > 499.999 && ! $version && $arc == "500" )
	and version = '5'
--% end
order by test desc