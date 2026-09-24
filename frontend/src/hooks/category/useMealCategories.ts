import { useCallback, useEffect, useState } from 'react'
import { CategoryApi } from '../../api/CategoryApi'
import type { MealCategory } from '../../types'
import { getErrorMessage } from '../../utils/getErrorMessage'

export const useMealCategories = () => {
	const [categories, setCategories] = useState<MealCategory[]>([])
	const [loadError, setLoadError] = useState<string | null>(null)

	const load = useCallback(async () => {
		try {
			setCategories(await CategoryApi.getCategories())
			setLoadError(null)
		} catch (error) {
			setLoadError(getErrorMessage(error, 'Nie udało się pobrać kategorii.'))
		}
	}, [])

	useEffect(() => {
		void load()
	}, [load])

	const save = async (name: string, category?: MealCategory) => {
		if (category) {
			await CategoryApi.updateCategory(category.id, name)
		} else {
			await CategoryApi.createCategory(name)
		}
		await load()
	}

	const remove = async (categoryId: number) => {
		await CategoryApi.deleteCategory(categoryId)
		await load()
	}

	return { categories, loadError, save, remove }
}
