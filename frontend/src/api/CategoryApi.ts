import { backendClient } from '../config/backendClient'
import type {
	MealCategoryCreateResponse,
	MealCategoryGetAllResponse,
	MealCategoryGetAllResponseListElement,
	MealCategoryRequest,
	MealCategoryUpdateResponse,
} from './dto/CategoryDto'

export const CategoryApi = {
	async getCategories(): Promise<MealCategoryGetAllResponseListElement[]> {
		const response = await backendClient.get<MealCategoryGetAllResponse>('/meal-categories')
		return response.data.mealCategories
	},

	async createCategory(name: string, image?: File): Promise<MealCategoryCreateResponse> {
		const request: MealCategoryRequest = { name }
		const formData = new FormData()
		formData.append(
			'category',
			new Blob([JSON.stringify(request)], { type: 'application/json' }),
		)
		if (image) formData.append('image', image)
		const response = await backendClient.post<MealCategoryCreateResponse>(
			'/meal-categories',
			formData,
		)
		return response.data
	},

	async updateCategory(
		id: number,
		name: string,
		image?: File,
	): Promise<MealCategoryUpdateResponse> {
		const request: MealCategoryRequest = { name }
		const formData = new FormData()
		formData.append(
			'category',
			new Blob([JSON.stringify(request)], { type: 'application/json' }),
		)
		if (image) formData.append('image', image)
		const response = await backendClient.put<MealCategoryUpdateResponse>(
			`/meal-categories/${id}`,
			formData,
		)
		return response.data
	},

	async deleteCategory(id: number): Promise<void> {
		await backendClient.delete(`/meal-categories/${id}`)
	},
}
