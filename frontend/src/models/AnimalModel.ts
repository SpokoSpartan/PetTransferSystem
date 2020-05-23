import { AnimalLocation } from '@/models/AnimalLocation';
import { TreatmentHistory } from '@/models/TreatmentHistory';

export class AnimalModel {


	id: number | undefined;
	name: string = '';
	species: string = '';
	race: string = '';
	description: string = '';
	birthDate: Date | undefined;
	sex: string = '';
	sterilized: boolean = false;
	shelterJoinDate: Date | undefined;
	animalLocation: AnimalLocation | {} | undefined;
	imageUrl: string = '';
	location: string = '';
	treatmentHistories: TreatmentHistory[] | [] | undefined;

}
