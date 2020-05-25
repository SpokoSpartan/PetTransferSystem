export class Organization {
    id: number = 0;
    phoneNumber: string = '';
    fullName: string = '';
    email: string = '';
    address: string = '';


	constructor(id: number, phoneNumber: string, fullName: string, email: string, address: string) {
		this.id = id;
		this.phoneNumber = phoneNumber;
		this.fullName = fullName;
		this.email = email;
		this.address = address;
	}

}
