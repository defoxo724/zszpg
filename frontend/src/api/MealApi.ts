import { backendClient } from '../config/backendClient'
import type {
	MealCreateRequest,
	MealCreateResponse,
	MealGetAllResponse,
	MealUpdateRequest,
	MealUpdateResponse,
} from './dto/MealDto'

export const MealApi = {
	async getMeals(): Promise<MealGetAllResponse['meals']> {
		const response = await backendClient.get<MealGetAllResponse>('/meals')
		return response.data.meals
	},

	async createMeal(dto: MealCreateRequest, image?: File): Promise<MealCreateResponse> {
		const formData = new FormData()
		const request: MealCreateRequest = {
			name: dto.name,
			description: dto.description,
			price: dto.price,
			mealCategoryId: dto.mealCategoryId,
		}
		formData.append('meal', new Blob([JSON.stringify(request)], { type: 'application/json' }))
		if (image) formData.append('image', image)

		const response = await backendClient.post<MealCreateResponse>('/meals', formData)
		return response.data
	},

	async updateMeal(id: number, dto: MealUpdateRequest): Promise<MealUpdateResponse> {
		const response = await backendClient.put<MealUpdateResponse>(`/meals/${id}`, dto)
		return response.data
	},

	async deleteMeal(id: number): Promise<void> {
		await backendClient.delete(`/meals/${id}`)
	},
}
