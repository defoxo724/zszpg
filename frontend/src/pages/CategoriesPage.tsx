import { useEffect, useState } from 'react'
import Button from 'react-bootstrap/Button'
import ButtonGroup from 'react-bootstrap/ButtonGroup'
import ListGroup from 'react-bootstrap/ListGroup'
import { CategoryApi } from '../api/CategoryApi'
import MealCategoryForm from '../components/MealCategoryForm'
import StatusMessage from '../components/StatusMessage'
import { useAuth } from '../context/AuthContext'
import type { MealCategory } from '../types'
import { getErrorMessage } from '../utils/getErrorMessage'

const CategoriesPage = () => {
	const { user } = useAuth()
	const isAdmin = user?.type === 'ADMIN'
	const [categories, setCategories] = useState<MealCategory[]>([])
	const [categoryBeingEdited, setCategoryBeingEdited] = useState<MealCategory | null>(null)
	const [error, setError] = useState<string | null>(null)

	const loadCategories = async () => {
		try {
			setCategories(await CategoryApi.getCategories())
			setError(null)
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się pobrać kategorii.'))
		}
	}

	useEffect(() => {
		void loadCategories()
	}, [])

	const handleSaveCategory = async (name: string) => {
		try {
			if (categoryBeingEdited) {
				await CategoryApi.updateCategory(categoryBeingEdited.id, name)
			} else {
				await CategoryApi.createCategory(name)
			}

			setCategoryBeingEdited(null)
			await loadCategories()
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się zapisać kategorii.'))
		}
	}

	const handleDeleteCategory = async (categoryId: number) => {
		const deletionConfirmed = confirm('Usunąć kategorię?')
		if (!deletionConfirmed) {
			return
		}

		try {
			await CategoryApi.deleteCategory(categoryId)
			await loadCategories()
		} catch (error) {
			setError(getErrorMessage(error, 'Nie udało się usunąć kategorii.'))
		}
	}

	return (
		<>
			<h1 className="mb-4">Kategorie posiłków</h1>
			<StatusMessage message={error} />
			{isAdmin && (
				<MealCategoryForm
					categoryBeingEdited={categoryBeingEdited}
					onSave={handleSaveCategory}
					onCancel={() => setCategoryBeingEdited(null)}
				/>
			)}
			<ListGroup>
				{categories.map((category) => (
					<ListGroup.Item
						className="d-flex justify-content-between align-items-center"
						key={category.id}
					>
						<span>{category.name}</span>
						{isAdmin && (
							<ButtonGroup size="sm">
								<Button
									variant="outline-primary"
									onClick={() => setCategoryBeingEdited(category)}
								>
									Edytuj
								</Button>
								<Button
									variant="outline-danger"
									onClick={() => handleDeleteCategory(category.id)}
								>
									Usuń
								</Button>
							</ButtonGroup>
						)}
					</ListGroup.Item>
				))}
			</ListGroup>
			{!categories.length && <p>Brak kategorii.</p>}
		</>
	)
}
export default CategoriesPage
