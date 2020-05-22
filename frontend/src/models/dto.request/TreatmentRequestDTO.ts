export class TreatmentRequestDTO {
	title: string = '';
	doctorName: string = '';
	startDate: Date | undefined;
	endDate: Date | undefined;
	description: string = '';
	price: number = 0;
	place: string = '';
}
