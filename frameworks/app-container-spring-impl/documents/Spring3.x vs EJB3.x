◆単体テストはSpring有利（圧倒的）	
	○EJB
	まともな単体テストツールがない。glassfishのEJBContainer.create()は動作が怪しい。
		・起動が遅い	
		・java:global指定のJNDIしか使用できない。java:/module指定は不可能。
		・test-classesにセッションBeanをコピーして読み込み対象ディレクトリを指定する必要がある。
		・jarファイルの中身まで読み込んでくれないのでフレームワーク等を読み込ませるのが不可能と思われる。
	実際にEJBコンテナを起動してテストするには重過ぎる上にカバレッジ取得不可能
	大規模で簡単にテストするにはEJBコンテナの実装を作リ込む必要がある。
	
	○Spring
	起動が早い。
	トランザクション管理をきちんと行いたい場合は-javaagentをvmオプションにつけておけばよい。
		・テストクラス毎に設定するのは馬鹿らしいので、プロジェクトが使用しているJDKのvmオプションに指定する。
		・これを指定しないと独自で取得したコネクションとJPAセッションのコネクションが不整合になり結果がおかしくなり、DBUnit使用時などは初期データが反映されないなど起こりうる。
	JMS-Producer、RequiresNewスコープなどのテストも可能、インターセプターも呼び出し可能。ただしJMS-Producerを成功させるにはMQを立ち上げておく必要がある。
	
◆移植性
	○EJB
	EJBコンテナがないと動作しない。Tomcat/Jettyでは動作させることはできない。
	アノテーションドリブンの場合侵略的なコードになる。(java.ejbのパッケージを使用するため）CDIを使用しても（javax.injectが必要）
	
	○Spring
	Spring自身がコンテナであるためどこでも動作する。JavaEEコンテナである必要はない。
	アノテーションドリブンの場合侵略的なコードになる。(springのパッケージを使用するため）
	
◆トランザクション管理
	○EJB
	JTA使用可能。
	SessionContextにrollbackフラグがあり、EJBコンテナが発生したExceptionの種類に従ってこのフラグを設定する。
	setRollbackOnlyをたたくことでアプリから制御することも可能。
	ただし、この処理を拡張することはできない。setRollbackOnlyを立てたらトランザクション境界でExceptionが発生するが抑制することができない。
	getRollbackOnlyがtrueだと以降でSessionBeanの生成ができない。基本的に即時終了しかない。
	
	○Spring
	JTA使用可能。
	Springのトランザクション管理はインターセプターで実施している。トランザクション境界でコミット、ロールバックを実行しているが、
	このインターセプターを自前で用意したり拡張することが可能。従ってかなり柔軟に手をいれらる。例えば、
	あるトランザクションの中でエラーメッセージが追加されていたらインターセプターでrollbackフラグを立てたり（EJBと同じように）、Exceptionの発生を制御できるなど可能。	
	デフォルトではrollbackフラグの概念はなくExceptionの発生有無でトランザクションをどうするかコンテナが決定する。
	トランザクションがロールバック状態になってもBeanの生成は可能。
	
◆DI
	○EJB
	リソースインジェクション。@EJB、@Resourceなど複数使用が前提。CDI（JSR 299）を利用することによりSpringのような汎用DIと同様にレイヤを意識する必要がなくなる。
	JNDIがベース。xmlファイルでも登録可能。WEB層では動作しない。（CDIを利用すれば可能）
	スコープはStatelessとStatefullとSingleton。
	ライフサイクルが複雑。コンテナ管理なのでこちらが把握するのは後述のライフサイクルイベントで判断するしかない？
	Statelessにフィールド変数を持たせるとコンテナが破棄するまで残ることになるため、基本的に使用不可能。Statefullもprototypeではないためフィールドが残ったりする。
	ライフサイクル毎にイベント呼び出し可能。passivate、activateなど。
	提供スコープの拡張は不可能。
	prototypeスコープがない。
	lookupする場合、Beanの名称がjava/global/applicationなど複数規約で規定されている。
	
	○Spring
	汎用インジェクション。WEB～インテグレーションまで同一のDI方法。どのクラスでもサービス登録さえすれば同じように利用可能。
	Spring独自管理。JNDIの利用も可能。xmlファイルでも登録可能。	
	スコープはprototypeとsingleton。	spring-webを使用することでリクエスト、セッション、アプリケーションのスコープ指定が可能になる。
	ライフサイクルは上記の通り。イベントはpostConstructとpreDestroyのみ。
	提供スコープを拡張できる。ページやConversationの実現も可能。
	DIに対する振る舞いを拡張できる。（自分でDI用のアノテーションを作成できるなど）
	lookupする場合Beanの名称は任意に指定可能。（アノテーションドリブンで使用している名称と同じ）

◎テスト時の工数と手の入れやすさでSpringの優位性がある。
　・テスト時にJUnitで動作させたり、軽量なTomcatで起動させて動作確認するなどが可能。EJBだと起動に時間かかってテストも容易ではない。
　・業務要件からくる機能拡張など手の入れやすさが楽。Springコンテナ自身にDIさせてこちらで作成したクラスを提供することが可能。（Springのポリシーはそれを前提にしている）

	