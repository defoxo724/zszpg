import type { FormEvent } from 'react'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import Stack from 'react-bootstrap/Stack'
import type { Meal } from '../../types'
import { formatPrice } from '../../utils/formatPrice'

interface MealSelectorFormProps {
	meals: Meal[]
	selectedMealId: number
	buttonLabel: string
	onMealSelect: (mealId: number) => void
	onSubmit: (mealId: number) => void
}

const MealSelectorForm = (props: MealSelectorFormProps) => {
	const handleSubmit = (event: FormEvent) => {
		event.preventDefault()
		if (props.selectedMealId) {
			props.onSubmit(props.selectedMealId)
		}
	}

	return (
		<Form onSubmit={handleSubmit} className="mb-4">
			<Stack direction="horizontal" gap={2}>
				<Form.Select
					size="sm"
					value={props.selectedMealId}
					onChange={(event) => props.onMealSelect(Number(event.target.value))}
				>
					{props.meals.map((meal) => (
						<option key={meal.id} value={meal.id}>
							{meal.name} — {formatPrice(Number(meal.price))}
						</option>
					))}
				</Form.Select>
				<Button type="submit" size="sm" disabled={!props.meals.length}>
					{props.buttonLabel}
				</Button>
			</Stack>
		</Form>
	)
}

export default MealSelectorForm
