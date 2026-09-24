import type { MealCategory } from '../../types'

export interface MealCategoryRequest {
	name: string
}

export type MealCategoryCreateResponse = MealCategory
export type MealCategoryUpdateResponse = MealCategory
export type MealCategoryGetAllResponseListElement = MealCategory

export interface MealCategoryGetAllResponse {
	mealCategories: MealCategory[]
}
