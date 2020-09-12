var app = new Vue ({
	el: '#login',
	data: {
		username : "",
		password : "",
		isPrijavljen: false
	},
	methods: {
		prijaviMe : function() {
			if(this.username != "" && this.password != "")
			{
				isPrijavljen = true;
			}
		}
	}
})