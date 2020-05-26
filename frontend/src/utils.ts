export const utils = {
	created() {
		console.log("utils are available")
	},
	methods: {
		getAgeWithBirthDate: function (value: string) {
			if (value == null || value == '') {
				return '*';
			}
			let date = new Date(value);
			return date.getDate() + "." + date.getMonth() + "." + date.getFullYear();
		},
		translateBooleanToHuman: function (value: boolean) {
			if (value) {
				return "yes"
			}
			return "no"
		}
	}
}
